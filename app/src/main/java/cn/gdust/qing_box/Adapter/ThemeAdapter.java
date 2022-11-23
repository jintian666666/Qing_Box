package cn.gdust.qing_box.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import cn.gdust.qing_box.Bean.Theme;
import cn.gdust.qing_box.R;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

//    private Context context;
    private List<Theme> data;

    public ThemeAdapter(List<Theme> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建一个view实例，把子项布局作为参数传进去，然后再把这个view传给ViewHolder的构造函数，最后返回这个viewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //设置控件的显示信息，以及设置Listener，响应事件
        Theme theme = data.get(position);
        holder.textView.setText(theme.getText());
        holder.cardView.setCardBackgroundColor(theme.getColor());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        //定义RecyclerView子项布局中需要变更的元素
        TextView textView;
        MaterialCardView cardView;

        public ViewHolder(@NonNull View view) {
            super(view);
            //通过findViewById()获取控件实例
            textView = view.findViewById(R.id.tv_color);
            cardView = view.findViewById(R.id.cardview);
        }
    }

}
