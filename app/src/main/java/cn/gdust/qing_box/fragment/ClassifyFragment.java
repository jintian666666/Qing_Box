package cn.gdust.qing_box.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.util.Arrays;

import cn.gdust.qing_box.R;
import cn.gdust.qing_box.click.itemOnClick;
import cn.gdust.qing_box.widget.AutoFlowLayout;
import cn.gdust.qing_box.widget.FlowAdapter;

public class ClassifyFragment extends Fragment {

    private ViewGroup root;
    private SharedPreferences sp;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private AutoFlowLayout flow1;
    private AutoFlowLayout flow2;
    private AutoFlowLayout flow3;
    private AutoFlowLayout flow4;
    private AutoFlowLayout flow5;
    private AutoFlowLayout flow6;
    private AutoFlowLayout flow7;
    private MaterialCardView card1;
    private MaterialCardView card2;
    private MaterialCardView card3;
    private MaterialCardView card4;
    private MaterialCardView card5;
    private MaterialCardView card6;
    private MaterialCardView card7;

    public final static String mData1 = "时间屏幕,指南针,OCR文字识别";
    public final static String mData2 = "快递查询,IP查询,号码归属地查询";
    public final static String mData3 = "计算器,日期计算器,进制转换";
    public final static String mData4 = "二维码工具,图片水印,九宫格切图";
    public final static String mData5 = "应用管理,坏点检测,查看设备信息";
    public final static String mData6 = "摩斯电码,数字转上下标,RC4加解密";
    public final static String mData7 = "历史上的今天,金属探测器,做决定/转盘";


    public static ClassifyFragment newInstance() {
        return new ClassifyFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);

        root = view.findViewById(R.id.root);
        sp = getContext().getSharedPreferences("sp", Activity.MODE_PRIVATE);

        flow1 = view.findViewById(R.id.flow1);
        flow2 = view.findViewById(R.id.flow2);
        flow3 = view.findViewById(R.id.flow3);
        flow4 = view.findViewById(R.id.flow4);
        flow5 = view.findViewById(R.id.flow5);
        flow6 = view.findViewById(R.id.flow6);
        flow7 = view.findViewById(R.id.flow7);
        AutoFlowLayout_1();
        AutoFlowLayout_2();
        AutoFlowLayout_3();
        AutoFlowLayout_4();
        AutoFlowLayout_5();
        AutoFlowLayout_6();
        AutoFlowLayout_7();

        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);
        img5 = view.findViewById(R.id.img5);
        img6 = view.findViewById(R.id.img6);
        img7 = view.findViewById(R.id.img7);
        Visible();

        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);
        card6 = view.findViewById(R.id.card6);
        card7 = view.findViewById(R.id.card7);
        Visible_bt();

        return view;
    }

    public void AutoFlowLayout_1(){
        flow1.setAdapter(new FlowAdapter(Arrays.asList(mData1.split(","))) {
            @Override
            public View getView(final int position) {
                View item = getLayoutInflater().inflate(R.layout.item_gn, null);
                Chip chip = item.findViewById(R.id.chip);
                chip.setText(mData1.split(",")[position]);
                chip.setOnClickListener(v -> itemOnClick.item_1(v.getContext(), mData1.split(",")[position]));
                chip.setOnLongClickListener(v -> {
                    return false;
                });
                return item;
            }
        });
    }

    public void AutoFlowLayout_2(){
        flow2.setAdapter(new FlowAdapter(Arrays.asList(mData2.split(","))) {
            @Override
            public View getView(final int position) {
                View item = getLayoutInflater().inflate(R.layout.item_gn, null);
                Chip chip = item.findViewById(R.id.chip);
                chip.setText(mData2.split(",")[position]);
                chip.setOnClickListener(v -> itemOnClick.item_2(v.getContext(),mData2.split(",")[position]));
                chip.setOnLongClickListener(v -> {
                    return false;
                });
                return item;
            }
        });
    }

    public void AutoFlowLayout_3(){
        flow3.setAdapter(new FlowAdapter(Arrays.asList(mData3.split(","))) {
            @Override
            public View getView(final int position) {
                View item = getLayoutInflater().inflate(R.layout.item_gn, null);
                Chip chip = item.findViewById(R.id.chip);
                chip.setText(mData3.split(",")[position]);
                chip.setOnClickListener(v -> itemOnClick.item_3(v.getContext(),mData3.split(",")[position]));
                chip.setOnLongClickListener(v -> {

                    return false;
                });
                return item;
            }
        });
    }

    public void AutoFlowLayout_4(){
        flow4.setAdapter(new FlowAdapter(Arrays.asList(mData4.split(","))) {
            @Override
            public View getView(final int position) {
                View item = getLayoutInflater().inflate(R.layout.item_gn, null);
                Chip chip = item.findViewById(R.id.chip);
                chip.setText(mData4.split(",")[position]);
                chip.setOnClickListener(v -> itemOnClick.item_4(v.getContext(),mData4.split(",")[position]));
                chip.setOnLongClickListener(v -> {
                    return false;
                });
                return item;
            }
        });
    }

    public void AutoFlowLayout_5(){
        flow5.setAdapter(new FlowAdapter(Arrays.asList(mData5.split(","))) {
            @Override
            public View getView(final int position) {
                View item = getLayoutInflater().inflate(R.layout.item_gn, null);
                Chip chip = item.findViewById(R.id.chip);
                chip.setText(mData5.split(",")[position]);
                chip.setOnClickListener(v -> itemOnClick.item_5(v.getContext(),mData5.split(",")[position]));
                chip.setOnLongClickListener(v -> {
                    return false;
                });
                return item;
            }
        });
    }

    public void AutoFlowLayout_6(){
        flow6.setAdapter(new FlowAdapter(Arrays.asList(mData6.split(","))) {
            @Override
            public View getView(final int position) {
                View item = getLayoutInflater().inflate(R.layout.item_gn, null);
                Chip chip = item.findViewById(R.id.chip);
                chip.setText(mData6.split(",")[position]);
                chip.setOnClickListener(v -> itemOnClick.item_6(v.getContext(),mData6.split(",")[position]));
                chip.setOnLongClickListener(v -> {
                    return false;
                });
                return item;
            }
        });
    }

    public void AutoFlowLayout_7(){
        flow7.setAdapter(new FlowAdapter(Arrays.asList(mData7.split(","))) {
            @Override
            public View getView(final int position) {
                View item = getLayoutInflater().inflate(R.layout.item_gn, null);
                Chip chip = item.findViewById(R.id.chip);
                chip.setText(mData7.split(",")[position]);
                chip.setOnClickListener(v -> itemOnClick.item_7(v.getContext(),mData7.split(",")[position]));
                chip.setOnLongClickListener(v -> {
                    return false;
                });
                return item;
            }
        });
    }

    public void Visible() {
        if (sp.getBoolean("1", true)) {
            flow1.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img1, "rotation", 0f, 90f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("1", true).apply();
        } else {
            flow1.setVisibility(View.GONE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img1, "rotation", 90f, 0f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("1", false).apply();
        }
        if (sp.getBoolean("2", true)) {
            flow2.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img2, "rotation", 0f, 90f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("2", true).apply();
        } else {
            flow2.setVisibility(View.GONE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img2, "rotation", 90f, 0f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("2", false).apply();
        }
        if (sp.getBoolean("3", true)) {
            flow3.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img3, "rotation", 0f, 90f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("3", true).apply();
        } else {
            flow3.setVisibility(View.GONE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img3, "rotation", 90f, 0f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("3", false).apply();
        }
        if (sp.getBoolean("4", true)) {
            flow4.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img4, "rotation", 0f, 90f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("4", true).apply();
        } else {
            flow4.setVisibility(View.GONE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img4, "rotation", 90f, 0f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("4", false).apply();
        }
        if (sp.getBoolean("5", true)) {
            flow5.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img5, "rotation", 0f, 90f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("5", true).apply();
        } else {
            flow5.setVisibility(View.GONE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img5, "rotation", 90f, 0f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("5", false).apply();
        }
        if (sp.getBoolean("6", true)) {
            flow6.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img6, "rotation", 0f, 90f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("6", true).apply();
        } else {
            flow6.setVisibility(View.GONE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img6, "rotation", 90f, 0f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("6", false).apply();
        }
        if (sp.getBoolean("7", true)) {
            flow7.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img7, "rotation", 0f, 90f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("7", true).apply();
        } else {
            flow7.setVisibility(View.GONE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img7, "rotation", 90f, 0f);
            objectAnimator.setDuration(0);
            objectAnimator.start();
            sp.edit().putBoolean("7", false).apply();
        }
    }

    public void Visible_bt() {
        card1.setOnClickListener(v -> {
            if (sp.getBoolean("1", true)) {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow1.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img1, "rotation", 90f, 0f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("1", false).apply();
            } else {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow1.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img1, "rotation", 0f, 90f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("1", true).apply();
            }
        });
        card2.setOnClickListener(v -> {
            if (sp.getBoolean("2", true)) {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow2.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img2, "rotation", 90f, 0f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("2", false).apply();
            } else {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow2.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img2, "rotation", 0f, 90f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("2", true).apply();
            }
        });
        card3.setOnClickListener(v -> {
            if (sp.getBoolean("3", true)) {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow3.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img3, "rotation", 90f, 0f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("3", false).apply();
            } else {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow3.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img3, "rotation", 0f, 90f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("3", true).apply();
            }
        });
        card4.setOnClickListener(v -> {
            if (sp.getBoolean("4", true)) {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow4.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img4, "rotation", 90f, 0f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("4", false).apply();
            } else {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow4.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img4, "rotation", 0f, 90f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("4", true).apply();
            }
        });
        card5.setOnClickListener(v -> {
            if (sp.getBoolean("5", true)) {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow5.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img5, "rotation", 90f, 0f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("5", false).apply();
            } else {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow5.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img5, "rotation", 0f, 90f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("5", true).apply();
            }
        });
        card6.setOnClickListener(v -> {
            if (sp.getBoolean("6", true)) {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow6.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img6, "rotation", 90f, 0f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("6", false).apply();
            } else {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow6.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img6, "rotation", 0f, 90f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("6", true).apply();
            }
        });
        card7.setOnClickListener(v -> {
            if (sp.getBoolean("7", true)) {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow7.setVisibility(View.GONE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img7, "rotation", 90f, 0f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("7", false).apply();
            } else {
                TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                flow7.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img7, "rotation", 0f, 90f);
                objectAnimator.setDuration(400);
                objectAnimator.start();
                sp.edit().putBoolean("7", true).apply();
            }
        });
    }

}