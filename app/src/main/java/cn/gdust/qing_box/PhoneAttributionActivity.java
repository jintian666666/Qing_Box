package cn.gdust.qing_box;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.tapadoo.alerter.Alerter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static cn.gdust.qing_box.utils.Utils.LoadingDialog;
import static cn.gdust.qing_box.utils.Utils.loadDialog;

public class PhoneAttributionActivity extends AppCompatActivity {

    @BindView(R.id.root)
    ViewGroup root;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.textInputEditText)
    TextInputEditText textInputEditText;
    @BindView(R.id.fab)
    ExtendedFloatingActionButton fab;
    @BindView(R.id.textView)
    AutoCompleteTextView textView;
    @BindView(R.id.card)
    MaterialCardView card;
    @BindView(R.id.copy)
    MaterialCardView copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_attribution);
        ButterKnife.bind(this);

        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.appbarColor)
                .navigationBarColor(R.color.backgroundColor)
                .autoDarkModeEnable(true)
                .init();

        toolbar.setTitle(getString(R.string.手机号归属地查询));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        textInputEditText = findViewById(R.id.textInputEditText);
        textInputLayout = findViewById(R.id.textInputLayout);
        textView = findViewById(R.id.textView);


        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textInputLayout.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        fab.setOnClickListener(view -> {
            if (TextUtils.isEmpty(textInputEditText.getText().toString())){
                textInputLayout.setError(getString(R.string.请输入手机号));
                textInputLayout.setErrorEnabled(true);
            }else {
                LoadingDialog(PhoneAttributionActivity.this);
                HttpRequest.build(PhoneAttributionActivity.this, "https://tenapi.cn/v2/phone?tel=" + textInputEditText.getText().toString())
                        .addHeaders("Content-Type", "application/json")
                        .setResponseListener(new ResponseListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(String response, Exception error) {
                                loadDialog.dismiss();
                                try {
                                    HashMap<String, Object> map = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>() {}.getType());
                                    // 获取response json转为map后的data键值对
                                    Map<String, Object> data = (Map<String, Object>)map.get("data");
                                    TransitionManager.beginDelayedTransition(root, new AutoTransition());
                                    card.setVisibility(View.VISIBLE);
                                    textView.setText(textInputEditText.getText().toString() + "\n" +
                                            "归属地：" + data.get("local") + "\n" +
                                            "号码段：" + data.get("num") + "\n" +
                                            "卡类型：" + data.get("type") + "\n" +
                                            "运营商：" + data.get("isp") + "\n" +
                                            "通信标准：：" + data.get("std"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).doPost();
            }
        });

        copy.setOnClickListener(v -> {
            ((ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", textView.getText().toString()));
            Alerter.create((Activity) v.getContext())
                    .setTitle(R.string.复制成功)
                    .setText(R.string.已成功将内容复制到剪切板)
                    .setBackgroundColorInt(getResources().getColor(R.color.success))
                    .show();
        });
    }
}