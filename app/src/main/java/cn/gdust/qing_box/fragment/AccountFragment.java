package cn.gdust.qing_box.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.gdust.qing_box.adapter.MeSettingAdapter;
import cn.gdust.qing_box.bean.MeSetting;
import cn.gdust.qing_box.R;

public class AccountFragment extends Fragment {

    ListView listView1,listView2;

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