package com.brandservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.MemoryFile
import android.os.ParcelFileDescriptor
import android.util.Log

class BrandService {

    private val TAG: String = "BrandServiceConnection"

    var brandStub: IBrandServiceAIDL? = null

    val intent: Intent = Intent("com.brandservice.service")
    init {
        intent.action = "com.brandservice.service"
        intent.setPackage("com.brandservice")
    }
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            brandStub = IBrandServiceAIDL.Stub.asInterface(binder)
            Log.d(TAG, "[onServiceConnected] called with: name = $name, binder = $binder")
        }
        override fun onServiceDisconnected(name: ComponentName) {
            brandStub = null
            Log.d(TAG, "[onServiceDisconnected]")
        }
    }

    private fun checkBrandStub(){
        if(brandStub == null){
            throw RuntimeException("与商标检测服务失去连接")
        }
    }

    fun getBrandResultFromService(pfd: ParcelFileDescriptor): BrandResult? {
        checkBrandStub()
        return brandStub?.getBrandResult(pfd)
    }

    fun getBrandResultFromService(byteArray: ByteArray): BrandResult?{
        // 创建MemoryFile
        val memoryFile= MemoryFile("client_image", byteArray.size)
        // 向MemoryFile中写入字节数组
        memoryFile.writeBytes(byteArray, 0, 0, byteArray.size)
        // 获取MemoryFile对应的FileDescriptor
        val fd= MemoryFileUtils.getFileDescriptor(memoryFile)
        // 根据FileDescriptor创建ParcelFileDescriptor
        val pfd= ParcelFileDescriptor.dup(fd)
        return getBrandResultFromService(pfd)
    }
}