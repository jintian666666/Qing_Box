package cn.gdust.qing_box;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.adapter.ThemeAdapter;
import cn.gdust.qing_box.bean.Theme;

public class ThemeActivity extends AppCompatActivity {

    private static final String FLAG = "主题管理";

    @BindView(R.id.title)
    TextView toolbarTitle;
    @BindView(R.id.rv_theme)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        ButterKnife.bind(this);

        toolbarTitle.setText(FLAG);

        List<Theme> data = new ArrayList<>();
        Theme colorDefault = new Theme(ContextCompat.getColor(this,R.color.themeDefault), "默认");
        data.add(colorDefault);
        Theme colorHawaii = new Theme(ContextCompat.getColor(this,R.color.themeHawaiiNight), "夏威夷夜晚");
        data.add(colorHawaii);
        Theme colorQingDai = new Theme(ContextCompat.getColor(this,R.color.themeYuanShanQingDai), "远山青黛");
        data.add(colorQingDai);
        Theme colorSeaSalt = new Theme(ContextCompat.getColor(this,R.color.themeSeaSaltCheese), "海盐芝士");
        data.add(colorSeaSalt);
        Theme colorLavender = new Theme(ContextCompat.getColor(this,R.color.themeLavender), "薰衣草");
        data.add(colorLavender);
        Theme colorDaisy= new Theme(ContextCompat.getColor(this,R.color.themeDaisy), "雏菊");
        data.add(colorDaisy);
        Theme colorOrange = new Theme(ContextCompat.getColor(this,R.color.themeOrange), "鲜橙");
        data.add(colorOrange);
        Theme colorPink = new Theme(ContextCompat.getColor(this,R.color.themeSakuraPink), "樱粉");
        data.add(colorPink);
        Theme colorWinter = new Theme(ContextCompat.getColor(this,R.color.themeWinter), "清冬");
        data.add(colorWinter);

        //RecyclerView需要设置布局管理器,这里设置线性布局管理器（ListView不需要）
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new ThemeAdapter(data));
    }
}