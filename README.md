# BrandClient
BrandClient是一个调用BrandService的Demo；
BrandService是提供算法服务的程序。
# 接口说明
（1）需要通过AIDL（[官方文档](https://developer.android.google.cn/guide/components/aidl.html?hl=zh-cn)）调用BrandService服务。
（2）请先下载BrandService.apk（下载链接），并安装。
# 调用方式
## 修改AndroidManifest.xml
在`AndroidManifest.xml`中添加如下代码，和 <application>标签 一级：
```xml
    <queries>
        <package android:name="com.brandservice" />
    </queries>
```
## 导入AIDL文件和其他包

1. 按照如下目录结构导入aidl文件夹：

![image.png](https://cdn.nlark.com/yuque/0/2023/png/23087530/1683624720809-3a898540-a6f1-45ae-97d7-31412913cffc.png#averageHue=%233e444b&clientId=u846ccdcc-872a-4&from=paste&height=176&id=ub6898374&originHeight=264&originWidth=427&originalType=binary&ratio=1&rotation=0&showTitle=false&size=11744&status=done&style=none&taskId=u3d060218-a2a6-4c0d-8b62-a70ef630b26&title=&width=284.6666666666667)

2. 按照如下目录结构导入com.brandservice文件夹：

![image.png](https://cdn.nlark.com/yuque/0/2023/png/23087530/1683625257339-3896594c-e3d4-4a06-8611-794eb1258f51.png#averageHue=%233d4348&clientId=u846ccdcc-872a-4&from=paste&height=315&id=u36a49ac5&originHeight=473&originWidth=423&originalType=binary&ratio=1&rotation=0&showTitle=false&size=24121&status=done&style=none&taskId=u671b1ed6-2335-4376-abc5-ecf72bb20bd&title=&width=282)

3. rebuild project 

![image.png](https://cdn.nlark.com/yuque/0/2023/png/23087530/1683624854276-06997fbf-9230-4b22-bddb-9e1cfd43253b.png#averageHue=%233d434a&clientId=u846ccdcc-872a-4&from=paste&height=272&id=ue25dc25a&originHeight=349&originWidth=371&originalType=binary&ratio=1&rotation=0&showTitle=false&size=22396&status=done&style=none&taskId=u91b35a7c-afaf-44bf-a2df-5fba51d0c40&title=&width=289.3333435058594)

4. 查看是否生成java(generated)文件夹

将目录结构切换到`Android`下，可能需要等待一会，才能看到该文件夹：
![image.png](https://cdn.nlark.com/yuque/0/2023/png/23087530/1683625385669-cba861c2-2d68-4ff9-8a62-338876038625.png#averageHue=%233b4044&clientId=u846ccdcc-872a-4&from=paste&height=308&id=u7f68bee4&originHeight=379&originWidth=360&originalType=binary&ratio=1&rotation=0&showTitle=false&size=20383&status=done&style=none&taskId=u943a306b-3617-464a-90f4-67541440bfb&title=&width=293)
# 调用接口获取结果
**Demo: **`**com.brandclient.MainActivity**`
## 初始化连接
```kotlin
class MainActivity : AppCompatActivity() {

    private var brandService: BrandService? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init service connection
        brandService = BrandService()
        val ok = bindService(brandService!!.intent, brandService!!.serviceConnection, Context.BIND_AUTO_CREATE)
    }
}
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/23087530/1683625545679-15298e6e-6718-4b77-9890-8b1128f563b7.png#averageHue=%23302c2b&clientId=u846ccdcc-872a-4&from=paste&height=329&id=u06ccbb90&originHeight=493&originWidth=1669&originalType=binary&ratio=1&rotation=0&showTitle=false&size=79873&status=done&style=none&taskId=u4ae595db-7a6e-4aa0-9eea-466b7f4c8b8&title=&width=1112.6666666666667)
## 调用方法
```kotlin
/** 2. 调用BrandService服务获取检测结果，也可传图片的ByteArray **/
val result = brandService?.getBrandResultFromService(descriptor)
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/23087530/1683634776174-abf828eb-b211-4039-8b38-653550397508.png#averageHue=%232f2c2b&clientId=u846ccdcc-872a-4&from=paste&height=441&id=u74763553&originHeight=484&originWidth=1042&originalType=binary&ratio=1&rotation=0&showTitle=false&size=109187&status=done&style=none&taskId=u0569562c-9a9c-478b-b722-cd6c15a5664&title=&width=949.6666870117188)
## 断开连接
```kotlin
    override fun onDestroy(){
        super.onDestroy()
        /** 3. unbindService **/
        if(brandService?.brandStub != null){
            unbindService(brandService!!.serviceConnection)
        }
    }
```
# 接口返回的数据

