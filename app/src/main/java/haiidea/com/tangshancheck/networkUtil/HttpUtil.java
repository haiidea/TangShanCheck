package haiidea.com.tangshancheck.networkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import haiidea.com.tangshancheck.BuildConfig;
import haiidea.com.tangshancheck.TangShanApplication;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/11/9.
 */

public class HttpUtil {
    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 30000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 10000;
    private static HttpUtil mHttpUtil;
    public Retrofit retrofit;
    private HttpApi httpApi;

    private HttpUtil(){
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG){
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        //缓存
        File cacheFile = new File(TangShanApplication.getApplication().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb
        Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetUtil.isNetworkConnected()) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 2)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
        /**
         * 设置头信息
         */
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body());
                requestBuilder.addHeader("Authorization", "Bearer " + "");//添加请求头信息，服务器进行token有效性验证
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://tangshang.com")
                .build();
        httpApi = retrofit.create(HttpApi.class);
    }
    public static HttpUtil getIntance(){
        if (mHttpUtil==null){
            mHttpUtil = new HttpUtil();
        }
        return mHttpUtil;
    }
    public HttpApi getApi(){
        return mHttpUtil.httpApi;
    }
    /**
     * 设置订阅 和 所在的线程环境
     */
    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {

        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(0)//请求失败重连次数
                .subscribe(s);

    }
}
