package cn.gdust.qing_box.click;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import cn.gdust.qing_box.AppActivity;
import cn.gdust.qing_box.CalculatorActivity;
import cn.gdust.qing_box.ClockActivity;
import cn.gdust.qing_box.CompassActivity;
import cn.gdust.qing_box.ConversionActivity;
import cn.gdust.qing_box.DateCalculatorActivity;
import cn.gdust.qing_box.HistoryActivity;
import cn.gdust.qing_box.IpSiteActivity;
import cn.gdust.qing_box.PhoneAttributionActivity;
import cn.gdust.qing_box.PictureNineActivity;
import cn.gdust.qing_box.PictureWaterActivity;
import cn.gdust.qing_box.QRCodeActivity;
import cn.gdust.qing_box.R;
import cn.gdust.qing_box.ScreenActivity;
import cn.gdust.qing_box.utils.Utils;


public class itemOnClick {

    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public static void item_1(Context context, String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        if (name.equals(context.getString(R.string.时间屏幕))) {
            context.startActivity(new Intent(context, ClockActivity.class));
        }
        if (name.equals(context.getString(R.string.指南针))) {
            context.startActivity(new Intent(context, CompassActivity.class));
        }
        if (name.equals(context.getString(R.string.OCR文字识别))) {
//            context.startActivity(new Intent(context, PictureColorActivity.class));
        }
    }

    public static void item_2(Context context, String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        if (name.equals(context.getString(R.string.快递查询))) {
//            context.startActivity(new Intent(context, PictureColorActivity.class));
        }
        if (name.equals(context.getString(R.string.IP查询))) {
            context.startActivity(new Intent(context, IpSiteActivity.class));
        }
        if (name.equals(context.getString(R.string.号码归属地查询))) {
            context.startActivity(new Intent(context, PhoneAttributionActivity.class));
        }
    }

    public static void item_3(Context context, String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        if (name.equals(context.getString(R.string.计算器))) {
            context.startActivity(new Intent(context, CalculatorActivity.class));
        }
        if (name.equals(context.getString(R.string.日期计算器))) {
            context.startActivity(new Intent(context, DateCalculatorActivity.class));
        }
        if (name.equals(context.getString(R.string.进制转换))) {
            context.startActivity(new Intent(context, ConversionActivity.class));
        }
    }

    public static void item_4(Context context, String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        if (name.equals(context.getString(R.string.二维码工具))) {
            context.startActivity(new Intent(context, QRCodeActivity.class));
        }
        if (name.equals(context.getString(R.string.图片水印))) {
            context.startActivity(new Intent(context, PictureWaterActivity.class));
        }
        if (name.equals(context.getString(R.string.九宫格切图))) {
            context.startActivity(new Intent(context, PictureNineActivity.class));
        }
    }

    public static void item_5(Context context, String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        if (name.equals(context.getString(R.string.应用管理))) {
            context.startActivity(new Intent(context, AppActivity.class));
        }
        if (name.equals(context.getString(R.string.坏点检测))) {
            context.startActivity(new Intent(context, ScreenActivity.class));
        }
        if (name.equals(context.getString(R.string.查看设备信息))) {
            Utils.CopyDialog(context,context.getString(R.string.设备信息),Utils.getPhoneInfo(context));
        }
    }

    public static void item_6(Context context, String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        if (name.equals(context.getString(R.string.摩斯电码))) {
//            context.startActivity(new Intent(context, PictureColorActivity.class));
        }
        if (name.equals(context.getString(R.string.数字转上下标))) {
//            context.startActivity(new Intent(context, AvatarMakeActivity.class));
        }
        if (name.equals(context.getString(R.string.RC4加解密))) {
//            context.startActivity(new Intent(context, PictureClarityActivity.class));
        }
    }

    public static void item_7(Context context, String name) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

        if (name.equals(context.getString(R.string.历史上的今天))) {
            context.startActivity(new Intent(context,HistoryActivity.class));
        }
        if (name.equals(context.getString(R.string.金属探测器))) {
//            context.startActivity(new Intent(context, AvatarMakeActivity.class));
        }
        if (name.equals(context.getString(R.string.做决定))) {
//            context.startActivity(new Intent(context, PictureClarityActivity.class));
        }
    }

}
