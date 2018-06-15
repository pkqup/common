package com.pkqup.commonlibrary.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

/**
 * Created by liucun on 2017/6/5.
 */

@GlideModule
public class MyGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        int maxMemory = (int) Runtime.getRuntime().maxMemory();// 获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;// 设置图片内存缓存占用八分之一

        // 设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        // 设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));

        // 设置磁盘缓存大小
        int diskCacheSize = 1024 * 1024 * 100;
        // 存放在/data/data/com.example.liucun.glidetest/cache
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));//context.getCacheDir()
        // 存放在外部存储 /storage/sdcard0/Android/data/com.example.liucun.glidetest/cache
        // builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
        // builder.setDiskCache(new DiskLruCacheFactory(context.getExternalCacheDir().getPath(), "glide", diskCacheSize));

        //设置加载图片的像素属性
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .disallowHardwareConfig());
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //使用okHttp加载图片
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;// 不去检查清单文件中的GlideModule
    }
}
