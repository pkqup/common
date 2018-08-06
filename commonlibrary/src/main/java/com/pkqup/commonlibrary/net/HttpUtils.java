package com.pkqup.commonlibrary.net;

import android.support.annotation.NonNull;

import com.pkqup.commonlibrary.net.bean.CommonParams;
import com.pkqup.commonlibrary.net.converter.MyGSonConverterFactory;
import com.pkqup.commonlibrary.net.exception.ApiException;
import com.pkqup.commonlibrary.util.AppUtils;
import com.pkqup.commonlibrary.util.NetWorkUtils;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by LiuCun on 2017/11/28.<br>
 * Describe
 */

public class HttpUtils {

    private static String BASE_URL = "http://mall.chunlangjiu.com/";

    private volatile static HttpUtils mInstance;
    private Retrofit mRetrofit;


    // 获取网络请求的单例（双重校验锁的单例模式）
    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    private HttpUtils() {
        // OkHttp缓存设置
        File httpCacheDirectory =
                new File(AppUtils.getContext().getCacheDir(), "ChunlangHttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(netInterceptor)// 添加网络判断
                .addInterceptor(logInterceptor)// 添加log拦截器
                .cache(cache)// 设置网络请求缓存配置
                .connectTimeout(10, TimeUnit.SECONDS)// 设置连接超时时间
                .readTimeout(10, TimeUnit.SECONDS)// 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)// 配置服务器地址
                .addConverterFactory(MyGSonConverterFactory.create(GSonUtils.getmInstance()))// 配置GSon数据转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 配置RxJava适配器
                .client(mClient)// 关联OkHttp
                .build();

    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private Interceptor netInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (NetWorkUtils.isNetworkConnected()) {
                return chain.proceed(chain.request());
            } else {
                throw new ApiException("网络未连接，请连接后重试", ApiException.NO_NETWORK);
            }
        }
    };

    private Interceptor logInterceptor = new Interceptor() {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            //请求
            long startTime = System.currentTimeMillis();
            Request oldRequest = chain.request();

            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());
            Request.Builder builder = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body());

            addCommonParams(authorizedUrlBuilder);

            // 新的请求
            Request newRequest = builder.url(authorizedUrlBuilder.build()).build();

            //响应
            Response response = chain.proceed(newRequest);
            //这个是因为，如果请求下载链接的话，会导致无法获取response
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE);


            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String content = response.body().string();
            KLog.e("----------Request Start----------------");
            KLog.e("| " + newRequest.toString());
            KLog.e("| RequestBody:" + bodyToString(newRequest.body()));
            KLog.e("| Response:" +content);
            KLog.e("----------Request End:" + duration + "毫秒----------");

            //在调用了response.body().string()方法之后，response中的流会被关闭，我们需要创建出一个新的response给应用层处理。
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), content))
                    .build();
        }
    };

    private void addCommonParams(HttpUrl.Builder authorizedUrlBuilder) {
        authorizedUrlBuilder.addQueryParameter("format", "json");
    }


    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


}
