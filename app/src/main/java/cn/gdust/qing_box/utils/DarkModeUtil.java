package cn.gdust.qing_box.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * 切换夜间模式工具类
 */
public class DarkModeUtil {

    public static final String KEY_CURRENT_MODEL = "night_mode_state_sp";
    private SwitchMaterial sw;

    private static int getNightModel(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY_CURRENT_MODEL, Context.MODE_PRIVATE);
        return sp.getInt(KEY_CURRENT_MODEL, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static void setNightModel(Context context, int nightMode) {
        SharedPreferences sp = context.getSharedPreferences(KEY_CURRENT_MODEL, Context.MODE_PRIVATE);
        sp.edit().putInt(KEY_CURRENT_MODEL, nightMode).apply();
    }

    /**
     * 这个方法在应用程序onCreate方法中调用
     *
     * @param application application
     */
    public static void init(Application application) {
        int nightMode = getNightModel(application);
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    /**
     * 应用夜间模式
     */
    public static void applyNightMode(Context context) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setNightModel(context, AppCompatDelegate.MODE_NIGHT_YES);
    }

    /**
     * 应用日间模式
     */
    public static void applyDayMode(Context context) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setNightModel(context, AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * 跟随系统主题时需要动态切换
     */
    public static void applySystemMode(Context context) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        setNightModel(context, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    /**
     * 判断App当前是否处于暗黑模式状态
     *
     * @param context 上下文
     * @return 返回
     */
    public static boolean isDarkMode(Context context) {
        int nightMode = getNightModel(context);
        if (nightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            int applicationUiMode = context.getResources().getConfiguration().uiMode;
            int systemMode = applicationUiMode & Configuration.UI_MODE_NIGHT_MASK;
            return systemMode == Configuration.UI_MODE_NIGHT_YES;
        } else {
            return nightMode == AppCompatDelegate.MODE_NIGHT_YES;
        }
    }

}
