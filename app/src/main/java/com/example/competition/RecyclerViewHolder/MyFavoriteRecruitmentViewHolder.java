package com.example.competition.RecyclerViewHolder;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.layout.XUILinearLayout;

import org.jetbrains.annotations.NotNull;

public class MyFavoriteRecruitmentViewHolder extends BaseViewHolder {

    private XUILinearLayout xuiLinearLayout;

    public MyFavoriteRecruitmentViewHolder(@NotNull View view) {
        super(view);
        xuiLinearLayout = view.findViewById(R.id.my_favorite_recruitment_xui_layout);
        // set radius and shadow
        int radius = DensityUtils.dp2px(MyApp.getContext(), 15);
        int elevation = DensityUtils.dp2px(MyApp.getContext(), 10);
        xuiLinearLayout.setRadiusAndShadow(radius, elevation, 0.25F);
    }
}
