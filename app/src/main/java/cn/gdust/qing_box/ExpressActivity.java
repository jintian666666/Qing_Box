package cn.gdust.qing_box;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.adapter.ExpressRvAdapter;
import cn.gdust.qing_box.api.ExpressClient;
import cn.gdust.qing_box.bean.Express;

import static cn.gdust.qing_box.utils.Utils.LoadingDialog;
import static cn.gdust.qing_box.utils.Utils.loadDialog;

public class ExpressActivity extends AppCompatActivity {

    //https://api.oioweb.cn/api/common/delivery?nu=75564949090856
    //75564949090856 中通
    //参考音乐搜索器、垃圾分类查询
    // LoadingDialog(MusicActivity.this); loadDialog.dismiss();
    @BindView(R.id.root)
    ViewGroup root;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.fab)
    ExtendedFloatingActionButton fab;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.textInputEditText)
    TextInputEditText textInputEditText;
    private List<Express> expressList = new ArrayList<>();
    ExpressClient client;


    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x01){
                loadDialog.dismiss();
                rv.setLayoutManager(new LinearLayoutManager(ExpressActivity.this));
                ExpressRvAdapter adapter = new ExpressRvAdapter(expressList);
                rv.setAdapter(adapter);
                rv.addItemDecoration(new DividerItemDecoration(ExpressActivity.this,DividerItemDecoration.VERTICAL));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        ButterKnife.bind(this);

        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.appbarColor)
                .navigationBarColor(R.color.backgroundColor)
                .autoDarkModeEnable(true)
                .init();

        toolbar.setTitle(getString(R.string.快递查询));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        client = new ExpressClient();

        fab.setOnClickListener(view -> {
            if (TextUtils.isEmpty(textInputEditText.getText())) {
                textInputLayout.setError(getString(R.string.请输入快递单号));
                textInputLayout.setErrorEnabled(true);
            } else {
                LoadingDialog(this);
                new Thread(() -> {
                    Message msg = new Message();
                    try {
                        expressList = client.requestApi(textInputEditText.getText().toString());
                        msg.what = 0x01;
                        msg.obj = expressList;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

    }

}