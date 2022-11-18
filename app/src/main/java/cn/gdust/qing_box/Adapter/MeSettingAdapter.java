package cn.gdust.qing_box.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.gdust.qing_box.Bean.MeSetting;
import cn.gdust.qing_box.R;

public class MeSettingAdapter extends BaseAdapter {

    private List<MeSetting> data;
    private Context context;

    public MeSettingAdapter(List<MeSetting> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_me_setting,viewGroup,false);
            viewHolder.textView = view.findViewById(R.id.item_tv);
            viewHolder.imageView = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(data.get(i).getImage());
        viewHolder.textView.setText(data.get(i).getTextView());
        return view;
    }

    private final class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
