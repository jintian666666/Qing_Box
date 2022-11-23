package cn.gdust.qing_box.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.Adapter.ThemeAdapter;
import cn.gdust.qing_box.Bean.Theme;
import cn.gdust.qing_box.R;

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
        for (int i =0;i<3;i++){
            Theme green = new Theme(Color.GREEN, "绿色");
            data.add(green);
            Theme black = new Theme(Color.BLACK, "黑色");
            data.add(black);
            Theme blue = new Theme(Color.BLUE, "蓝色");
            data.add(blue);
        }

        //RecyclerView需要设置布局管理器,这里设置线性布局管理器（ListView不需要）
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new ThemeAdapter(data));
    }
}