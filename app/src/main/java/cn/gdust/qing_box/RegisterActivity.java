package cn.gdust.qing_box;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String FLAG = "用户注册";

    @BindView(R.id.title)
    TextView toolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        toolbarTitle.setText(FLAG);

    }
}