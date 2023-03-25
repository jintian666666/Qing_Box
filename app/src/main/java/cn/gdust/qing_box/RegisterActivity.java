package cn.gdust.qing_box;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.sql.Connection;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.dbutils.DBUtils;

public class RegisterActivity extends AppCompatActivity {

    private static final String FLAG = "用户注册";

    @BindView(R.id.title)
    TextView toolbarTitle;
    @BindView(R.id.bt_register)
    MaterialButton bt_register;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password2)
    EditText et_password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        toolbarTitle.setText(FLAG);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = DBUtils.getConnection();
            }
        }).start();
    }
}