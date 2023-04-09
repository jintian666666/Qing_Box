package cn.gdust.qing_box.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;

import java.util.ArrayList;
import java.util.HashMap;

import cn.gdust.qing_box.CollectActivity;
import cn.gdust.qing_box.R;
import cn.gdust.qing_box.click.itemOnClick;

public class FavorFragment extends Fragment {

    private MaterialCardView add;
    private MaterialCardView banner;
    private ViewGroup root;
    private RecyclerView rv;
    private LinearLayout no;
    private ExtendedFloatingActionButton fab;
    private XBanner banner_view;

    public static SharedPreferences collect;

    public static FavorFragment newInstance() {
        return new FavorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favor, container, false);

        add = view.findViewById(R.id.add);
        root = view.findViewById(R.id.root);
        rv = view.findViewById(R.id.rv);
        no = view.findViewById(R.id.no);
        fab = view.findViewById(R.id.fab);
        banner = view.findViewById(R.id.banner);
        banner_view = view.findViewById(R.id.banner_view);
        banner_view.setPageTransformer(Transformer.Default);

        collect = getContext().getSharedPreferences("collect", Activity.MODE_PRIVATE);
        add.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CollectActivity.class));
        });
        fab.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CollectActivity.class));
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ArrayList<HashMap<String, Object>> listmap = new Gson().fromJson(collect.getString("collect",null), new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
            if (listmap.size() == 0) {
                no.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            } else {
                rv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                CompanyInfoAdapter companyInfoAdapter = new CompanyInfoAdapter(getContext(), listmap);
                no.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                rv.setAdapter(companyInfoAdapter);
                rv.getAdapter().notifyDataSetChanged();
            }
        } catch (Exception e) {
        }
    }

    public static class CustomViewsInfo implements BaseBannerInfo {

        private String imgurl;
        private String tzurl;

        public CustomViewsInfo(String imgurl, String tzurl) {
            this.imgurl = imgurl;
            this.tzurl = tzurl;
        }

        @Override
        public String getXBannerUrl() {
            return imgurl;
        }

        @Override
        public String getXBannerTitle() {
            return tzurl;
        }
    }

    public class CompanyInfoAdapter extends RecyclerView.Adapter<CompanyInfoAdapter.MyViewHolder> implements Filterable {
        LayoutInflater inflater;
        Context mContext;
        ArrayList<HashMap<String, Object>> mDatas;
        ArrayList<HashMap<String, Object>> filterDatas;

        /**
         * @param context
         * @param datas
         */
        public CompanyInfoAdapter(Context context, ArrayList<HashMap<String, Object>> datas) {
            this.mContext = context;
            this.mDatas = datas;
            this.filterDatas = datas;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(mContext, R.layout.item_button, null);
            return new CompanyInfoAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CompanyInfoAdapter.MyViewHolder holder, int position) {
            holder.button.setText((CharSequence) filterDatas.get(position).get("name"));
            holder.button.setOnClickListener(v -> {
                itemOnClick.item_1(getContext(), String.valueOf(filterDatas.get(position).get("name")));
                itemOnClick.item_2(getContext(), String.valueOf(filterDatas.get(position).get("name")));
                itemOnClick.item_3(getContext(), String.valueOf(filterDatas.get(position).get("name")));
                itemOnClick.item_4(getContext(), String.valueOf(filterDatas.get(position).get("name")));
                itemOnClick.item_5(getContext(), String.valueOf(filterDatas.get(position).get("name")));
                itemOnClick.item_6(getContext(), String.valueOf(filterDatas.get(position).get("name")));
                itemOnClick.item_7(getContext(), String.valueOf(filterDatas.get(position).get("name")));
            });
        }
        @Override
        public int getItemCount() {
            return filterDatas.size();
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                //执行过滤操作
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        //没有过滤的内容，则使用源数据
                        filterDatas = mDatas;
                    } else {
                        ArrayList<HashMap<String, Object>> filteredList = new ArrayList<>();


                        for (int i = 0; i < mDatas.size(); i++) {

                            if (String.valueOf(mDatas.get(i).get("name")).toLowerCase().contains(charString)) {
                                filteredList.add(mDatas.get(i));
                            }
                        }

                        filterDatas = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterDatas;
                    return filterResults;
                }

                //把过滤后的值返回出来
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filterDatas = (ArrayList<HashMap<String, Object>>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            MaterialButton button;


            public MyViewHolder(View itemView) {
                super(itemView);
                button = (MaterialButton) itemView.findViewById(R.id.button1);

            }
        }

    }

}