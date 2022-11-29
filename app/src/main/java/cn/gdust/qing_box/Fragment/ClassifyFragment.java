package cn.gdust.qing_box.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import cn.gdust.qing_box.R;
import cn.gdust.qing_box.Utils.ClassifyInit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassifyFragment extends Fragment {

    LinearLayout common,query,calculate,image,device,file,other;
    LinearLayout detail_common,detail_query,detail_calculate,detail_image,detail_device,detail_file,detail_other;
    MaterialButton bt_time;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClassifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassifyFragment newInstance(String param1, String param2) {
        ClassifyFragment fragment = new ClassifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_classify, container, false);
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
        new ClassifyInit(common,R.drawable.ic_sun,"常用应用",getContext(),detail_common);
        new ClassifyInit(query,R.drawable.ic_search,"查询应用",getContext(),detail_query);
        new ClassifyInit(calculate,R.drawable.ic_calculate, "计算应用",getContext(),detail_calculate);
        new ClassifyInit(image,R.drawable.ic_image, "图片应用",getContext(),detail_image);
        new ClassifyInit(device,R.drawable.ic_device, "设备应用",getContext(),detail_device);
        new ClassifyInit(file,R.drawable.ic_file, "文件应用",getContext(),detail_file);
        new ClassifyInit(other,R.drawable.ic_other, "其它应用",getContext(),detail_other);
        return view;
    }

    private void initButton(){

    }

}