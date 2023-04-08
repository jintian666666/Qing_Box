package cn.gdust.qing_box.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.gdust.qing_box.R;
import cn.gdust.qing_box.bean.Express;

public class ExpressRvAdapter extends RecyclerView.Adapter<ExpressRvAdapter.Holder>{

    private List<Express> exList;

    public ExpressRvAdapter(List<Express> exList) {
        this.exList = exList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_express,parent,false);
        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Express express = exList.get(position);
        holder.tv_context.setText(express.getContext());
        holder.tv_time.setText(express.getTime());
        //如果是最新一条物流，则改变context颜色
    }

    @Override
    public int getItemCount() {
        return exList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView tv_context;
        TextView tv_time;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_context = itemView.findViewById(R.id.tv_context);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

}
