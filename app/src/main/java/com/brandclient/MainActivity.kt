package com.brandclient

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.brandservice.BrandService
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity() {

    private var brandService: BrandService? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val title_text = findViewById<TextView>(R.id.title_text)

        /** 1. init service connection **/
        brandService = BrandService()
        val ok = bindService(brandService!!.intent, brandService!!.serviceConnection, Context.BIND_AUTO_CREATE)

        if (ok) {
            title_text.text = "bind success"
        } else {
            title_text.text = "bind failed"
        }
        val btn_select = findViewById<Button>(R.id.btn_select)
        // select picture and get result
        btn_select.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        val brand_text = findViewById<TextView>(R.id.brand_text)
        val iv_pic = findViewById<ImageView>(R.id.iv_pic)
        val target_text = findViewById<TextView>(R.id.target_text)
        val target_view = findViewById<ImageView>(R.id.target_view)
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK && data != null){
                try {
                    val descriptor = data.data?.let { this.contentResolver.openFileDescriptor(it, "r") }
                    if (descriptor != null) {
                        /** 2. 调用BrandService服务获取检测结果，也可传图片的ByteArray **/
                        /** 返回的结果内容参照接口文件 **/
                        val result = brandService?.getBrandResultFromService(descriptor)

                        // 验证结果
                        val hasResult = result?.detectorResult?.hasResult
                        val detectorResult = result?.detectorResult
                        if(hasResult == true){
                            brand_text.text = "total cost time: "+result.totalCostTime.toString()+"ms; "+result.createTime
                            iv_pic.setImageBitmap(detectorResult?.detectedImage)
                            val target = detectorResult?.targets?.get(0)
                            target_text.text = "detect time: "+detectorResult?.costTime+"ms; brand name: "+ target?.brandName +"; score: "+ target?.score
                            target_view.setImageBitmap(target?.image)
                        }else{
                            brand_text.text = "没有检测到商标"
                        }

                    }
                } catch (e: FileNotFoundException) {
                    throw RuntimeException(e)
                }
            }
        }
    }



    override fun onDestroy(){
        super.onDestroy()
        /** 3. unbindService **/
        if(brandService?.brandStub != null){
            unbindService(brandService!!.serviceConnection)
        }
    }
}
