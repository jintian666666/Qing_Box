package cn.gdust.qing_box.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tapadoo.alerter.Alerter;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.gdust.qing_box.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Utils {

    public static Bitmap pictureDrawable2Bitmap(PictureDrawable pictureDrawable){
        //图片背景的画笔
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        //创建bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(pictureDrawable.getIntrinsicWidth(), pictureDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0,0,bitmap.getWidth()+50,bitmap.getHeight()+50, paint);
        pictureDrawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        pictureDrawable.draw(canvas);
        return bitmap;
    }



    //复制弹窗
    public static void CopyDialog(Context context, String little, String content) {
        final AlertDialog mDialog = new MaterialAlertDialogBuilder(context)
                .setPositiveButton(R.string.复制, null)
                .setNegativeButton(R.string.取消, null)
                .create();
        mDialog.setTitle(little);
        mDialog.setMessage(content);
        mDialog.setOnShowListener(dialog -> {
            Button positiveButton = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            positiveButton.setOnClickListener(v -> {
                mDialog.dismiss();
                ((ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", content));
                Alerter.create((Activity) context)
                        .setTitle(R.string.复制成功)
                        .setText(R.string.已成功将内容复制到剪切板)
                        .setBackgroundColorInt(context.getResources().getColor(R.color.success))
                        .show();
            });
            negativeButton.setOnClickListener(v -> mDialog.dismiss());
        });
        mDialog.show();
        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels / 10 * 9;
        mDialog.getWindow().setAttributes(layoutParams);
    }

    //裁剪图片
    @SuppressLint("WrongConstant")
    public static String startUCrop(Context context, String sourceFilePath, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, ".Crop.jpg");
        //裁剪后图片的绝对路径
        String cameraScalePath = outFile.getAbsolutePath();
        Uri destinationUri = Uri.fromFile(outFile);
        //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(false);
        //设置toolbar颜色
        //options.setToolbarColor(ActivityCompat.getColor(activity, R.color.appbarColor));
        //设置状态栏颜色
        //options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.appbarColor));
        //是否能调整裁剪框
        //options.setFreeStyleCropEnabled(true);
        //UCrop配置
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
        //uCrop.useSourceImageAspectRatio();
        //跳转裁剪页面
        uCrop.start((Activity) context);
        return cameraScalePath;
    }

    //手机信息
    public static String getPhoneInfo(Context context){
        StringBuffer sb = new StringBuffer();
        sb.append("主板： "+ Build.BOARD + "\n\n");
        sb.append("系统启动程序版本号： "+ Build.BOOTLOADER + "\n\n");
        sb.append("系统定制商： "+ Build.BRAND + "\n\n");

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            sb.append("cpu指令集：" + Build.CPU_ABI + "\n\n");
            sb.append("cpu指令集2:" + Build.CPU_ABI2 + "\n\n");
        }else {

            if(Build.SUPPORTED_32_BIT_ABIS.length != 0){
                sb.append("cpu指令集:");
                sb.append(" [ 32位 ] ");
                sb.append("[ ");
                for (int i = 0; i < Build.SUPPORTED_32_BIT_ABIS.length; i++) {

                    if (i == Build.SUPPORTED_32_BIT_ABIS.length - 1) {
                        sb.append(Build.SUPPORTED_32_BIT_ABIS[i]);
                    } else {
                        sb.append(Build.SUPPORTED_32_BIT_ABIS[i] + " , ");
                    }

                }
                sb.append(" ]");
                sb.append("\n\n");
            }

            if(Build.SUPPORTED_64_BIT_ABIS.length != 0){
                sb.append("cpu指令集:");
                sb.append(" [ 64位 ] ");
                sb.append("[ ");
                for(int i=0;i<Build.SUPPORTED_64_BIT_ABIS.length;i++){

                    if(i == Build.SUPPORTED_64_BIT_ABIS.length - 1){
                        sb.append(Build.SUPPORTED_64_BIT_ABIS[i]);
                    }else{
                        sb.append(Build.SUPPORTED_64_BIT_ABIS[i] + " , ");
                    }

                }
                sb.append(" ]");
                sb.append("\n\n");
            }

        }
        sb.append("设置参数： "+ Build.DEVICE + "\n\n");
        sb.append("显示屏参数： "+ Build.DISPLAY + "\n\n");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            sb.append("无线电固件版本： "+ Build.getRadioVersion() + "\n\n");
        }
        sb.append("硬件识别码： "+ Build.FINGERPRINT + "\n\n");
        sb.append("硬件名称： "+ Build.HARDWARE + "\n\n");
        sb.append("HOST: "+ Build.HOST + "\n\n");
        sb.append("修订版本列表： "+ Build.ID + "\n\n");
        sb.append("硬件制造商： "+ Build.MANUFACTURER + "\n\n");
        sb.append("版本： "+ Build.MODEL + "\n\n");
        sb.append("硬件序列号：" + Build.SERIAL + "\n\n");
        sb.append("手机制造商：" + Build.PRODUCT + "\n\n");
        sb.append("描述Build的标签：" + Build.TAGS + "\n\n");
        sb.append("TIME: "+ Build.TIME + "\n\n");
        sb.append("builder类型：" + Build.TYPE + "\n\n");
        sb.append("USER: "+ Build.USER );

        return sb.toString();
    }

    //保存图片
    public static String SaveImage(Context context, Bitmap bitmap, String path, String name) {
        if (bitmap == null) {
            return null;
        }
        @SuppressLint("SimpleDateFormat")
        final String time = new SimpleDateFormat("HH-mm-ss").format(new Date());
        if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat(path))) {
            FileUtil.makeDir(FileUtil.getExternalStorageDir().concat(path));
        }
        File appDir = new File(FileUtil.getExternalStorageDir().concat(path));
        File file = new File(appDir, name);
        java.io.FileOutputStream fos = null;
        try {
            fos = new java.io.FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //手机壁纸
    public static Bitmap getWallpaper_1(Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        }
        ParcelFileDescriptor mParcelFileDescriptor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mParcelFileDescriptor = wallpaperManager.getWallpaperFile(WallpaperManager.FLAG_SYSTEM);
        }
        FileDescriptor fileDescriptor = mParcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        try {
            mParcelFileDescriptor.close();
        } catch(Exception e) {
        }
        return image;
    }

    //锁屏壁纸
    public static Bitmap getWallpaper_2(Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        }
        ParcelFileDescriptor mParcelFileDescriptor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mParcelFileDescriptor = wallpaperManager.getWallpaperFile(WallpaperManager.FLAG_LOCK);
        }
        FileDescriptor fileDescriptor = mParcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        try {
            mParcelFileDescriptor.close();
        } catch(Exception e) {
        }
        return image;
    }

    /**
     * Base64加密字符串
     * @param content -- 代加密字符串
     * @param charsetName -- 字符串编码方式
     * @return
     */
    public static String base64Encode(Context context, String content, String charsetName) {
        if (TextUtils.isEmpty(charsetName)) {
            charsetName = "UTF-8";
        }
        try {
            byte[] contentByte = content.getBytes(charsetName);
            return Base64.encodeToString(contentByte, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //判断vpn
    public static boolean isVPNConnected(Context context) {
        List<String> networkList = new ArrayList<>();
        try {
            for (java.net.NetworkInterface networkInterface : java.util.Collections.list(java.net.NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp()) {networkList.add(networkInterface.getName());}
            }
        } catch (Exception ex) {
        }
        return networkList.contains("tun0") || networkList.contains("ppp0");
    }

    public static AlertDialog loadDialog;
    //加载弹窗
    public static void LoadingDialog(Context context) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch(Exception e) {
        }
        loadDialog = new MaterialAlertDialogBuilder(context)
                .create();
        final View contentView = View.inflate(context, R.layout.loading, null);
        loadDialog.setView(contentView);
        loadDialog.show();
        loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        WindowManager.LayoutParams layoutParams = loadDialog.getWindow().getAttributes();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels / 5 * 4;
        layoutParams.height = context.getResources().getDisplayMetrics().widthPixels / 5 * 4;
        loadDialog.getWindow().setAttributes(layoutParams);
    }

    //侧滑水波纹
    public static void setRipple(Context context, View view, int bgColor, int rippleColor, int left_top, int right_top, int left_bottom, int right_bottom) {
        GradientDrawable GD = new GradientDrawable();
        GD.setColor(bgColor);
        GD.setCornerRadii(new float[]{dp2px(context, left_top), dp2px(context, left_top), dp2px(context, right_top), dp2px(context, right_top), dp2px(context, right_bottom), dp2px(context, right_bottom), dp2px(context, left_bottom), dp2px(context, left_bottom)});
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{rippleColor}), GD, null);
        view.setBackground(RE);
    }

    //dp转px
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    //截取字符串
    public static String JieQu(Context context, String str1, String str2, String str3) {
        if (!str1.contains(str2) || !str1.contains(str3)) {
            return "";
        }
        String substring = str1.substring(str1.indexOf(str2) + str2.length());
        return substring.substring(0, substring.indexOf(str3));
    }

}
