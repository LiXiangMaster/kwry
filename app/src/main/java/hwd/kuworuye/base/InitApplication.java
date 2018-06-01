package hwd.kuworuye.base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss_android_sdk.BuildConfig;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.model.HttpHeaders;

import cn.jpush.android.api.JPushInterface;

/**
 * @author maminghua
 * @ClassName: mApplication
 * @Description: 全局配置
 */
public class InitApplication extends Application {
    private static final String TAG = "InitApplication";
    private static Context mContext;
    //OSS的Bucket
//    public static final String OSS_BUCKET = "hwd-kuworuye";
    public static final String OSS_BUCKET = "kuwo";
    //设置OSS数据中心域名或者cname域名   http://kuwo.oss-cn-shanghai.aliyuncs.com
//    public static final String OSS_BUCKET_HOST_ID = "oss-cn-shanghai.aliyuncs.com";
    public static final String OSS_BUCKET_HOST_ID = "oss-cn-shanghai.aliyuncs.com";
    //Key
//    private static final String accessKey = "LTAImepRhzY1H8Ju";
//    private static final String screctKey = "UiwusOBP6neyZsG8bHWn3wgQvcVzxw";
    //阿里云最新的但是都可以用很奇怪的。
    private static final String accessKey = "LTAIfYlBAxMOUxn7";
    private static final String screctKey = "pmAInwBTaySznzgdFn2EYqYW1WQTAZ";

    public static OSS oss;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initDisplayMetrics();
        addHttpHeader(this);
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
        //初始化OSS配置
        initOSSConfig();
    }

    private void initOSSConfig(){
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKey, screctKey);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        if(BuildConfig.DEBUG){
            OSSLog.enableLog();
        }
        oss = new OSSClient(getApplicationContext(), InitApplication.OSS_BUCKET_HOST_ID, credentialProvider, conf);
    }



    public static  void addHttpHeader(Application app){
        //添加公共请求头；
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Access-Token","");
        headers.put("X-Key", String.valueOf(0));
        OkHttpUtils.init(app);

        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
//                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
                //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonHeaders(headers)     ;
    }


    public static Context getRealContext() {
        return mContext;
    }

    /**
     * 功能：初始化屏幕显示参数（APP字体不随系统设置大小字体而变化）
     */
    private void initDisplayMetrics() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
    }
}
