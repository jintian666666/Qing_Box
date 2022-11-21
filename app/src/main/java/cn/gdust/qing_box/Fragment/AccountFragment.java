package cn.gdust.qing_box.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.gdust.qing_box.Adapter.MeSettingAdapter;
import cn.gdust.qing_box.Bean.MeSetting;
import cn.gdust.qing_box.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    ListView listView1,listView2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        listView1 = view.findViewById(R.id.lv1);
        listView2 = view.findViewById(R.id.lv2);

        List<MeSetting> data = new ArrayList<>();
        MeSetting phone = new MeSetting(R.drawable.ic_mobile,"手机号："+"13377771234");
        data.add(phone);
        MeSetting email = new MeSetting(R.drawable.ic_email,"邮箱："+"未绑定");
        data.add(email);
        MeSetting userId = new MeSetting(R.drawable.ic_pen,"用户名ID："+"未绑定");
        data.add(userId);
        List<MeSetting> data2 = new ArrayList<>();
        MeSetting updatePass = new MeSetting(R.drawable.ic_password,"修改密码");
        data2.add(updatePass);
        MeSetting deleteAccount = new MeSetting(R.drawable.ic_no_accounts,"删除账户");
        data2.add(deleteAccount);
        listView1.setAdapter(new MeSettingAdapter(data, getActivity()));
        listView2.setAdapter(new MeSettingAdapter(data2,getActivity()));

        return view;
    }
}