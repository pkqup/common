package com.pkqup.commonlibrary.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pkqup.commonlibrary.R;

public class GlideUtils {

    String[] images =
            new String[] {"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
                    "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",};

    String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497688355699&di=ea69a930b82ce88561c635089995e124&imgtype=0&src=http%3A%2F%2Fcms-bucket.nosdn.127.net%2Ff84e566bcf654b3698363409fbd676ef20161119091503.jpg";
    String url2 = "http://img1.imgtn.bdimg.com/it/u=4027212837,1228313366&fm=23&gp=0.jpg";

    String gif1 = "http://img.zcool.cn/community/01e97857c929630000012e7e3c2acf.gif";
    String gif2 = "http://5b0988e595225.cdn.sohucs.com/images/20171202/a1cc52d5522f48a8a2d6e7426b13f82b.gif";
    String gif3 = "http://img.zcool.cn/community/01d6dd554b93f0000001bf72b4f6ec.jpg";
    String gif4 = "https://img.soogif.com/NvIkjiDmRgXyXcjrne68ANtYPHQ621gr.gif";
    String gif5 = "https://img.soogif.com/Pf7wbm6aIA5f9m2knjNwW4elO3kvAGKy.gif";
    String gif6 = "https://img.soogif.com/GV8BjdGd2YoG3b9IrThH3jfTNR1CExqx.gif";
    String gif7 = "https://img.soogif.com/xOUTB9LcXNU6EZrmUareuaUeAS3oKGf6.gif";
    String gif8 = "https://img.soogif.com/mQKkdii02YdX2Bta4yDzoVQhvfsyigL1.gif";
    String gif9 = "https://img.soogif.com/9t3kAMiQ5GZ9UaKRu325jd0z0jj5JoK6.gif";


    public static void loadImage(Context context, Object url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .format(DecodeFormat.PREFER_RGB_565)//像素类型
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有尺寸
                .error(R.mipmap.error_pic)
                .into(imageView);
    }

    public static void loadImageShop(Context context, Object url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .format(DecodeFormat.PREFER_RGB_565)//像素类型
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有尺寸
                .error(R.mipmap.error_shop)
                .into(imageView);
    }

    public static void loadImageHead(Context context, Object url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .format(DecodeFormat.PREFER_RGB_565)//像素类型
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有尺寸
                .error(R.mipmap.common_head_icon)
                .into(imageView);
    }

    public static void loadImageOptions(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .applyDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).centerCrop())
                .load(url)
                .into(imageView);
    }
}
