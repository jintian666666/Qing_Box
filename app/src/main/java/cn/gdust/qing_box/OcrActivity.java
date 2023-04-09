package cn.gdust.qing_box;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.gyf.immersionbar.ImmersionBar;
import com.tapadoo.alerter.Alerter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.api.OcrClient;
import cn.gdust.qing_box.utils.FileUtil;

import static cn.gdust.qing_box.utils.Utils.LoadingDialog;
import static cn.gdust.qing_box.utils.Utils.loadDialog;

public class OcrActivity extends AppCompatActivity {

    @BindView(R.id.root_img)
    ViewGroup root_img;
    @BindView(R.id.root_refresh)
    ViewGroup root_refresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.card)
    MaterialCardView card;
    @BindView(R.id.textView)
    AutoCompleteTextView textView;
    @BindView(R.id.copy)
    MaterialCardView copy;
    @BindView(R.id.button1)
    MaterialButton button1;
    @BindView(R.id.button2)
    MaterialButton button2;

    private static final String TAG = "OcrActivity";

    private Intent image = new Intent(Intent.ACTION_GET_CONTENT);
    public final int REQ_CD_IMAGE = 101;
    private Bitmap bitmap = null;
    private File imgPath;

    OcrClient ocrClient;
    String identifyText; //识别出来的文字


    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x01){
                loadDialog.dismiss();
                TransitionManager.beginDelayedTransition(root_refresh, new AutoTransition());
                textView.setText(identifyText);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        ButterKnife.bind(this);

        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.appbarColor)
                .navigationBarColor(R.color.backgroundColor)
                .autoDarkModeEnable(true)
                .init();

        toolbar.setTitle(getString(R.string.OCR文字识别));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        image.setType("image/*");
        image.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);

        ocrClient = new OcrClient();

        button1.setOnClickListener(v -> startActivityForResult(image, REQ_CD_IMAGE));

        button2.setOnClickListener(view -> {
            if (bitmap == null){
                Toast.makeText(OcrActivity.this, "请先选择一张图片", Toast.LENGTH_SHORT).show();
            }else{
                LoadingDialog(this);
                new Thread(() -> {
                    Message msg = new Message();
                    try {
                        identifyText = uploadImage();
                        msg.what = 0x01;
                        msg.obj = identifyText;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
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

    /**
     * 上传图片到API识别
     * @return 识别文本
     * @throws Exception
     */
    private String uploadImage() throws Exception {
        if (bitmap != null) {
            // 将所选照片转换成base64字符串
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            String urlEncodedString = URLEncoder.encode(base64Image, "UTF-8");
            // 在这里调用API接口，将urlEncode作为参数进行请求
            String responseText = ocrClient.requestApi(urlEncodedString);
            return responseText;
        }else {
            return "";
        }
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        if (_requestCode == REQ_CD_IMAGE && _resultCode == Activity.RESULT_OK) {
            ArrayList<String> _filePath = new ArrayList<>();
            if (_data != null) {
                if (_data.getClipData() != null) {
                    for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                        ClipData.Item _item = _data.getClipData().getItemAt(_index);
                        _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                    }
                } else {
                    _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                }

                TransitionManager.beginDelayedTransition(root_img, new androidx.transition.AutoTransition());
                img.setVisibility(View.VISIBLE);
                imgPath = new File(_filePath.get(0));
                bitmap = FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 1024, 1024);
                img.setImageBitmap(bitmap);

            }
        }
    }

}