package cn.gdust.qing_box;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.bean.User;
import cn.gdust.qing_box.dao.UserDao;

import static cn.gdust.qing_box.utils.Utils.LoadingDialog;
import static cn.gdust.qing_box.utils.Utils.loadDialog;

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

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            loadDialog.dismiss();
            String toast = (String) msg.obj;
            Toast.makeText(RegisterActivity.this, toast ,Toast.LENGTH_SHORT).show();
        }
    };

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
        Message msg = new Message();
        LoadingDialog(this);
        new Thread(() -> {
            String phone = et_account.getText().toString();
            String passWord = et_password.getText().toString();
            User user = new User(phone,passWord);
            UserDao userDao = new UserDao();
            if (userDao.add(user)){
                msg.what = 0x01;
                msg.obj = "注册成功";
            }else {
                msg.what = 0x01;
                msg.obj = "注册失败";
            }
            handler.sendMessage(msg);
        }).start();
    }
}