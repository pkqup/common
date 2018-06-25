package com.pkqup.commonlibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SizeUtils {

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(float dpValue) {
        final float scale = AppUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Value of px to value of dp.
     *
     * @param pxValue The value of px.
     * @return value of dp
     */
    public static int px2dp(float pxValue) {
        final float scale = AppUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Value of sp to value of px.
     *
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int sp2px(float spValue) {
        final float fontScale = AppUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Value of px to value of sp.
     *
     * @param pxValue The value of px.
     * @return value of sp
     */
    public static int px2sp(float pxValue) {
        final float fontScale = AppUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Get the width of the screen.
     * 获得屏幕宽度
     * @return Return the width of the screen.
     */
    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) AppUtils.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Get the height of the screen.
     * 获得屏幕高度
     * @return Return the height of the screen.
     */
    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) AppUtils.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * Whether the Status bar is hidden or not,the method always helps you get
     * the height of Status bar.
     * 获得状态栏的高度
     *
     * @param context The context to use. Usually your
     *                {@link android.app.Application} or
     *                {@link Activity} object.
     * @return Return the height of Status bar.
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int id = (Integer) (clazz.getField("status_bar_height").get(object));
            statusHeight = context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 功能描述：获取整块屏幕的高度
     *
     * @param context
     * @return int
     */
    public static int getRealScreenHeight(Context context) {
        int dpi = 0;
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 功能描述：获取虚拟按键区域的高度
     *
     * @param context
     * @return int 如果有虚拟按键则返回其高度否则返回0；
     */
    public static int getNavigationAreaHeight(Context context) {
        int realScreenHeight = SizeUtils.getRealScreenHeight(context);
        int screenHeight = SizeUtils.getScreenHeight();

        return realScreenHeight - screenHeight;
    }

    /**
     * 获取导航栏高度
     *
     * @param c
     * @return
     */
    public static int getNavigationBarrH(Context c) {
        Resources resources = c.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(identifier);
    }


    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth();
        int height = getScreenHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获得标题栏高度
     *
     * @param context 上下文，为Activity对象
     * @return 标题栏高度
     */
    public static int getTitleBarHeight(Activity context) {
        int contentTop = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight(context);
    }

    /**
     * 获取通知栏高度
     *
     * @param context 上下文
     * @return 通知栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取指定Activity的截屏，保存到png文件
     *
     * @param activity activity
     * @return 截屏Bitmap
     */
    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * 保存bitmap
     *
     * @param b           bitmap
     * @param strFileName 文件名
     * @return 是否保存成功
     */
    private static boolean savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 截取webView快照(webView加载的整个内容的大小)
     *
     * @param webView webview
     * @return 截屏bitmap
     */
    private static Bitmap captureWebView(WebView webView) {
        Picture snapShot = webView.capturePicture();

        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }

    /**
     * 根据毫秒获得格式化日期
     *
     * @param time   毫秒数
     * @param format 格式化字符串
     * @return 格式化后的字符串
     */
    private static String getDate(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String daystr = formatter.format(date);
        return daystr;
    }

    /**
     * 是否存在sd卡
     *
     * @return 是否存在sd卡
     */
    private static Boolean isExistsSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    /**
     * 获得文件名
     *
     * @param context 上下文
     * @return 文件名
     */
    private static String getFileName(Context context) {
        String fileName = getDate(System.currentTimeMillis(), "yyyyMMddHHmmss") + ".png";
        final String localPath;
        if (isExistsSD()) {
            localPath = context.getExternalCacheDir() + File.separator + fileName;
        } else {
            localPath = context.getFilesDir() + fileName;
        }

        return localPath;
    }

    /**
     * 截屏并保存
     *
     * @param a activity
     * @return 保存的路径
     */
    public static String shoot(Activity a) {
        String localPath = getFileName(a);
        boolean ret = SizeUtils.savePic(SizeUtils.takeScreenShot(a), localPath);
        if (ret) {
            return localPath;
        } else {
            return "";
        }
    }

    /**
     * 截屏并保存
     *
     * @param context 上下文
     * @param webView webview
     * @return 保存的路径
     */
    public static String shootWebView(Context context, WebView webView) {
        String localPath = getFileName(context);
        boolean ret = SizeUtils.savePic(SizeUtils.captureWebView(webView), localPath);
        if (ret) {
            return localPath;
        } else {
            return "";
        }
    }

    /**
     * Return the height of view.
     *
     * @param view The view.
     * @return the height of view
     */
    public static int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }

    /**
     * Measure the view.
     *
     * @param view The view.
     * @return arr[0]: view's width, arr[1]: view's height
     */
    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }


    private SizeUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
}
