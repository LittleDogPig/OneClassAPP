package eclass.dogking.com.oneclass.utils;

import android.os.Environment;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dog on 2018/1/17 0017.
 */

public class OneclassUtils {
    private static Retrofit retrofit = null;
    private static OneclassUtils instance = null;
    private static OkHttpClient mOkHttpClient;
    private static Cache cache;

    //private static final String IP = "192.168.23.1";//工厂
     //private static final String IP = "192.168.0.101";//郦城
      private static final String IP = "192.168.1.101";//怀德

    private static final String BASEURL = "http://" + IP + ":8080/";


    public static String getBaseURL(){
        return BASEURL;
    }

    static {
        initOkHttpClient();

    }

    //初始化Retrofit
    public static <T> T createAPI(Class<T> cs) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASEURL)
                    .build();

        }
        return retrofit.create(cs);
    }
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (OneclassUtils.class) {
                if (mOkHttpClient == null) {
                    File cacheFile = new File(Environment.getDownloadCacheDirectory(), "HttpCache");
                    cache = new Cache(cacheFile, 1024 * 1024 * 20);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
//                            .addNetworkInterceptor(new CacheInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                    Log.d("weijie", "initOkHttpClient成功: ");
                }
            }
        }
    }

}
