package cn.gdust.qing_box;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.gdust.qing_box.utils.BitmapSlicer;
import cn.gdust.qing_box.utils.FileUtil;
import cn.gdust.qing_box.utils.NinePicBitmapSlicer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static cn.gdust.qing_box.utils.Utils.LoadingDialog;
import static cn.gdust.qing_box.utils.Utils.loadDialog;
import static cn.gdust.qing_box.utils.Utils.startUCrop;

public class PictureNineActivity extends AppCompatActivity {

    @BindView(R.id.root)
    ViewGroup root;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public final int REQ_CD_IMAGE = 101;
    private Intent image = new Intent(Intent.ACTION_GET_CONTENT);
    private BitmapSlicer ninePicBitmapSlicer = new NinePicBitmapSlicer();
    private Bitmap bitmap;
    private BitmapSlicer bitmapSlicer = ninePicBitmapSlicer;
    private List<Bitmap> lastDesBitmaps;
    private List<ImageView> ninePicImageViews = new ArrayList<>();
    private List<ImageView> currentImageViewList = ninePicImageViews;
    private View progressView;
    private View resultView;
    private TextView resultTv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_nine);
        ButterKnife.bind(this);

        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.appbarColor)
                .navigationBarColor(R.color.backgroundColor)
                .autoDarkModeEnable(true)
                .init();

        toolbar.setTitle(getString(R.string.九宫格切图));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        image.setType("image/*");
        image.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);

        progressView = findViewById(R.id.layout_progress);
        resultView = findViewById(R.id.layout_result);
        resultTv = findViewById(R.id.tv_result);

        ninePicImageViews.add(findViewById(R.id.iv_image1));
        ninePicImageViews.add(findViewById(R.id.iv_image2));
        ninePicImageViews.add(findViewById(R.id.iv_image3));
        ninePicImageViews.add(findViewById(R.id.iv_image4));
        ninePicImageViews.add(findViewById(R.id.iv_image5));
        ninePicImageViews.add(findViewById(R.id.iv_image6));
        ninePicImageViews.add(findViewById(R.id.iv_image7));
        ninePicImageViews.add(findViewById(R.id.iv_image8));
        ninePicImageViews.add(findViewById(R.id.iv_image9));
    }

    public void choose(View v){
        startActivityForResult(image, REQ_CD_IMAGE);
    }

    public void save(View v) {
        if (lastDesBitmaps == null) {
            return;
        }
        LoadingDialog(PictureNineActivity.this);
        final String time = new SimpleDateFormat("HHmmss").format(new Date());
        if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/轻Box/九宫格切图/").concat(time).concat("/"))) {
            FileUtil.makeDir(FileUtil.getExternalStorageDir().concat("/轻Box/九宫格切图/").concat(time).concat("/"));
        }
        final File parent = new File(FileUtil.getExternalStorageDir().concat("/轻Box/九宫格切图/").concat(time).concat("/"));
        final String prefix = System.currentTimeMillis() + "";
        final ArrayList<File> slices = new ArrayList<>();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        Disposable disposable = Observable.fromArray(lastDesBitmaps.toArray(new Bitmap[]{}))
                .map(bitmap -> {
                    int index = lastDesBitmaps.indexOf(bitmap);
                    File file = new File(parent, prefix + "_" + (index + 1) + ".jpg");
                    OutputStream os = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();
                    return file;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    Uri uri = Uri.fromFile(file);
                    slices.add(file);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                }, throwable -> {
                    throwable.printStackTrace();
                    loadDialog.dismiss();
                    resultView.setVisibility(View.GONE);
                }, () -> {
                    loadDialog.dismiss();
                    resultView.setVisibility(View.VISIBLE);
                    resultTv.setText(Html.fromHtml(getString(R.string.切片已保存) + parent.getAbsolutePath() + "</font><font color=\"#868686\"></font>"));
                    resultTv.setTag(slices);
                });
    }

    private BitmapSlicer.BitmapSliceListener bitmapSliceListener = new BitmapSlicer.BitmapSliceListener() {
        @Override
        public void onSliceSuccess(Bitmap srcBitmap, List<Bitmap> desBitmaps) {
            srcBitmap.recycle();
            bitmapSlicer.setSrcBitmap(null);
            for (ImageView imageView : ninePicImageViews) {
                imageView.setImageBitmap(null);
                imageView.setVisibility(View.GONE);
            }
            if (lastDesBitmaps != null) {
                for (Bitmap lastDesBitmap : lastDesBitmaps) {
                    lastDesBitmap.recycle();
                }
            }
            lastDesBitmaps = null;
            for (int i = 0; i < currentImageViewList.size(); i++) {
                currentImageViewList.get(i).setImageBitmap(desBitmaps.get(i));
                currentImageViewList.get(i).setVisibility(View.VISIBLE);
            }
            lastDesBitmaps = desBitmaps;
            progressView.setVisibility(View.GONE);
            resultView.setVisibility(View.GONE);
        }

        @Override
        public void onSliceFailed() {
            progressView.setVisibility(View.GONE);
            resultView.setVisibility(View.GONE);
        }
    };

    // TODO: 2023/3/25 选择打开照片闪退bug
    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        if (_resultCode == RESULT_OK && _requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(_data);
            bitmap = FileUtil.decodeSampleBitmapFromPath(resultUri.getPath(), 1024, 1024);
            bitmapSlicer.setSrcBitmap(bitmap)
                    .registerListener(bitmapSliceListener)
                    .slice();
            progressView.setVisibility(View.VISIBLE);
        } else if (_resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(_data);
        }

        switch (_requestCode) {
            case REQ_CD_IMAGE:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        }
                        else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    startUCrop(PictureNineActivity.this, _filePath.get(0),1,1);
                }
                break;
            default:
                break;
        }
    }

}