package cn.gdust.qing_box.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cn.gdust.qing_box.R;

public class FavorFragment extends Fragment {

    SwipeRefreshLayout swipeRefresh;
    TextView tv_num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favor, container, false);
        swipeRefresh = view.findViewById(R.id.swiperRefresh);
        tv_num = view.findViewById(R.id.tv_num);
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(() -> {
            String num = tv_num.getText().toString();
            int value = Integer.parseInt(num);
            String text = String.valueOf(value+1);
            new Handler().postDelayed(() -> {
                swipeRefresh.setRefreshing(false);
                tv_num.setText(text);
            },1000);
        });
        return view;
    }
}