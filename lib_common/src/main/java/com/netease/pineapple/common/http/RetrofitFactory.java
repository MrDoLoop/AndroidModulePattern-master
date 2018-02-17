package com.netease.pineapple.common.http;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.netease.pineapple.common.utils.LogUtils;
import com.netease.pineapple.common.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitFactory {

    private static OkHttpClient sOkHttpClient;
    public static String sBASE_URL = "";
    private volatile static Retrofit sRetrofit;

    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
    private static final Interceptor cacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                // 有网络时 设置缓存为默认值
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    public static void init(String baseUrl) {
        sBASE_URL = baseUrl;

//        // 指定缓存路径,缓存大小 50Mb
//        Cache cache = new Cache(new File(PPUtils.getAppContext().getCacheDir(), "HttpCache"),
//                1024 * 1024 * 50);

        // log监听
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.i(message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //.cache(cache)
                .addInterceptor(cacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new NetMonitorInterceptor())
                .addInterceptor(logInterceptor)
                .retryOnConnectionFailure(true);

        sOkHttpClient = builder.build();
    }

    @NonNull
    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(sBASE_URL)
                            .client(sOkHttpClient)
                            // 添加string的转换器
                            .addConverterFactory(ScalarsConverterFactory.create())
                            // 添加Gson转换器
                            .addConverterFactory(GsonConverterFactory.create())
                            // 添加Retrofit到RxJava的转换器
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    public static Retrofit getRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(sBASE_URL)
                // 添加string的转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(sOkHttpClient)
                .build();
    }
}
