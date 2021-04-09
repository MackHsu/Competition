package com.example.competition.RecyclerViewHolder;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Model.DiscussReply;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.layout.XUILinearLayout;

import org.jetbrains.annotations.NotNull;

public class DiscussReplyViewHolder extends BaseViewHolder {

    private int itemType;
    private XUILinearLayout xuiLinearLayout;
    private View view;

    public DiscussReplyViewHolder(@NotNull View view) {
        super(view);
        this.view = view;
    }

    public void setXuiLinearLayout(int itemType) {
        this.itemType = itemType;
        if (itemType == DiscussReply.DISCUSS) {
            xuiLinearLayout = view.findViewById(R.id.discuss_xui_layout);
        } else if (itemType == DiscussReply.REPLY) {
            xuiLinearLayout = view.findViewById(R.id.discuss_reply_xui_layout);
        }
    }

    public void setRadiusAndShadow() {
        // set radius and shadow
        if (itemType == DiscussReply.REPLY) {
            int radius = DensityUtils.dp2px(MyApp.getContext(), 15);
            int elevation = DensityUtils.dp2px(MyApp.getContext(), 10);
            xuiLinearLayout.setRadiusAndShadow(radius, elevation, 0.25F);
        } else if (itemType == DiscussReply.DISCUSS) {
            xuiLinearLayout.setRadiusAndShadow(0, 0, 0F);
        }
    }
}
