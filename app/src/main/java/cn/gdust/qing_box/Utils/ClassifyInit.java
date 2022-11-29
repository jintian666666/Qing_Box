package cn.gdust.qing_box.Utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import cn.gdust.qing_box.R;
import lombok.Data;

@Data
public class ClassifyInit {

    private LinearLayout mainCard;
    private ImageView iv_classify;
    private ImageView iv_arrow;
    private TextView tv_classify;
    private LinearLayout layout_item;
    private boolean isOpen = true; //是否展开


    public ClassifyInit(LinearLayout mainCard, int img, String title, Context context,LinearLayout layout_detail) {
        iv_classify = mainCard.findViewById(R.id.iv_classify);
        tv_classify = mainCard.findViewById(R.id.tv_classify);
        iv_arrow = mainCard.findViewById(R.id.iv_arrow);
        layout_item = mainCard.findViewById(R.id.layout_item);
        iv_classify.setImageResource(img);
        tv_classify.setText(title);
        layout_item.setOnClickListener(view -> {
            if (isOpen){
                isOpen = false;
                layout_detail.setVisibility(View.GONE);
                DrawableCompat.setTint(iv_classify.getDrawable(), ContextCompat.getColor(context, R.color.colorIconClassify));
                iv_arrow.setImageResource(R.drawable.ic_navigate_next);
            }else {
                layout_detail.setVisibility(View.VISIBLE);
                iv_arrow.setImageResource(R.drawable.ic_down);
                DrawableCompat.setTint(iv_classify.getDrawable(), ContextCompat.getColor(context, R.color.colorPrimary));
                isOpen = true;
            }
        });

    }


}
