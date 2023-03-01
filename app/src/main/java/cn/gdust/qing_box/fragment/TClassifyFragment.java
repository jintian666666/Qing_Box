package cn.gdust.qing_box.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import cn.gdust.qing_box.R;
import cn.gdust.qing_box.utils.ClassifyInit;

public class TClassifyFragment extends Fragment {

    LinearLayout common,query,calculate,image,device,file,other;
    LinearLayout detail_common,detail_query,detail_calculate,detail_image,detail_device,detail_file,detail_other;
    MaterialButton bt_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tclassify, container, false);
        common = view.findViewById(R.id.common);
        detail_common = view.findViewById(R.id.detail_common);
        query = view.findViewById(R.id.query);
        detail_query = view.findViewById(R.id.detail_query);
        calculate = view.findViewById(R.id.calculate);
        detail_calculate = view.findViewById(R.id.detail_calculate);
        image = view.findViewById(R.id.image);
        detail_image = view.findViewById(R.id.detail_image);
        device = view.findViewById(R.id.device);
        detail_device = view.findViewById(R.id.detail_device);
        file = view.findViewById(R.id.file);
        detail_file = view.findViewById(R.id.detail_file);
        other = view.findViewById(R.id.other);
        detail_other = view.findViewById(R.id.detail_other);
        bt_time = view.findViewById(R.id.bt_time);
        bt_time.setOnClickListener(view1 -> {
            Toast.makeText(getActivity(), bt_time.getText().toString(), Toast.LENGTH_SHORT).show();
        });
        new ClassifyInit(common,R.drawable.ic_sun,"常用工具",getContext(),detail_common);
        new ClassifyInit(query,R.drawable.ic_search,"查询工具",getContext(),detail_query);
        new ClassifyInit(calculate,R.drawable.ic_calculate, "计算工具",getContext(),detail_calculate);
        new ClassifyInit(image,R.drawable.ic_image, "图片工具",getContext(),detail_image);
        new ClassifyInit(device,R.drawable.ic_device, "设备工具",getContext(),detail_device);
        new ClassifyInit(file,R.drawable.ic_file, "文字工具",getContext(),detail_file);
        new ClassifyInit(other,R.drawable.ic_other, "其它工具",getContext(),detail_other);
        return view;
    }

    private void initButton(){

    }

}