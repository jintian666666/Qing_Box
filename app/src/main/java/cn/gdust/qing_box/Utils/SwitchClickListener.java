package cn.gdust.qing_box.Utils;

import android.content.Context;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.switchmaterial.SwitchMaterial;

import cn.gdust.qing_box.R;

public class SwitchClickListener extends SwitchMaterial {

    SwitchMaterial mSwitch;
    /**
     * Menu中的item与Switch点击监听构造
     * @param context
     * @param item
     */
    public SwitchClickListener(@NonNull Context context, MenuItem item) {
        super(context);
        LinearLayout linearLayout = (LinearLayout) item.getActionView();
        mSwitch = linearLayout.findViewById(R.id.darkModeSwitch);
    }

    /**
     * switch开关联动夜间模式开关
     */
    public void darkMode(DarkModeUtil darkModeUtil,Context context){
        if (!mSwitch.isChecked()){
            darkModeUtil.applyNightMode(context);
            mSwitch.setChecked(true);
        }else {
            darkModeUtil.applyDayMode(context);
            mSwitch.setChecked(false);
        }

    }



}
