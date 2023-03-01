package cn.gdust.qing_box;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.adapter.MeSettingAdapter;
import cn.gdust.qing_box.bean.MeSetting;
import cn.gdust.qing_box.R;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.lv)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);

        List<MeSetting> data = new ArrayList<>();
        MeSetting phone = new MeSetting(R.drawable.ic_mobile,"手机号："+"13377778888");
        data.add(phone);
        MeSetting email = new MeSetting(R.drawable.ic_email,"邮箱："+"未绑定");
        data.add(email);
        MeSetting userId = new MeSetting(R.drawable.ic_pen,"用户名ID："+"未绑定");
        data.add(userId);
        listView.setAdapter(new MeSettingAdapter(data,this));
    }
}