package com.pkqup.commonlibrary.net;

import android.support.annotation.NonNull;

import com.pkqup.commonlibrary.net.bean.CommonParams;
import com.pkqup.commonlibrary.net.converter.MyGSonConverterFactory;
import com.pkqup.commonlibrary.util.AppUtils;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by LiuCun on 2017/11/28.<br>
 * Describe
 */

public class HttpUtils {

    private static String BASE_URL = "http://api.tianapi.com/";

    private volatile static HttpUtils mInstance;
    private Retrofit mRetrofit;

    private Interceptor logInterceptor = new Interceptor() {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            //请求
            long startTime = System.currentTimeMillis();
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            addCommonParams(request, requestBuilder);
            request = requestBuilder.build();

            //响应
            Response response = chain.proceed(request);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String content = response.body().string();
            KLog.e("----------Request Start----------------");
            KLog.e("| " + request.toString());
            KLog.e("| " + bodyToString(request.body()));
            KLog.e("| Response:" + content);
            KLog.e("----------Request End:" + duration + "毫秒----------");

            //在调用了response.body().string()方法之后，response中的流会被关闭，我们需要创建出一个新的response给应用层处理。
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), content))
                    .build();
        }
    };

    private void addCommonParams(Request request, Request.Builder requestBuilder) {
        Map<String, String> params = new HashMap<>();

        //加入单独的参数
        FormBody formBody = (FormBody) request.body();
        if (null != formBody) {
            for (int i = 0; i < formBody.size(); i++) {
                params.put(formBody.encodedName(i), formBody.encodedValue(i));
            }
        }

/*        //加入公共参数
        String userId = "0";
        String userToken = "";
        CommonParams commonParams = new CommonParams();
        commonParams.setUserId(userId);
        commonParams.setUserToken(userToken);
        commonParams.setOs("android");
        commonParams.setPlatform("android");
        commonParams.setApiVersion("v1");
        commonParams.setPlatformVersion(String.valueOf(AppUtils.getVersionCode()));
        commonParams.setOsVersion(android.os.Build.VERSION.SDK_INT + "");
        params.put("params", commonParams);

        String json = GSonUtils.getmInstance().toJson(params);
        KLog.e("----------Request Start----------------");
        KLog.e("| " + request.toString());
        KLog.e(json);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);*/

        //加入公共参数
        String userId = "0";
        String userToken = "";
        params.put("userId", userId);
        params.put("userToken", userToken);
        params.put("sign", "");
        params.put("apiVersion", "v1");
        params.put("os", "android");
        params.put("platform", "android");
        params.put("platformVersion", String.valueOf(AppUtils.getVersionCode()));
        params.put("osVersion", android.os.Build.VERSION.SDK_INT + "");

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBodyBuilder.add(URLDecoder.decode(entry.getKey()), URLDecoder.decode(entry.getValue()));
            }
        }
        requestBuilder.post(formBodyBuilder.build());
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    private HttpUtils() {
        // OkHttp缓存设置
        File httpCacheDirectory =
                new File(AppUtils.getContext().getCacheDir(), "PkqHttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient mClient = new OkHttpClient.Builder()
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

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }
}
