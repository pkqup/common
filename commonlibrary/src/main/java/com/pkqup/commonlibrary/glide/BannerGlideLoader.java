package com.pkqup.commonlibrary.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * @CreatedbBy: liucun on 2018/6/25
 * @Describe: banner的图片加载使用Glide
 */
public class BannerGlideLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideUtils.loadImage(context, path, imageView);
    }
}
