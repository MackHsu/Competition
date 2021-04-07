package com.example.competition.RecyclerViewAdapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Model.Competition;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.RecyclerViewHolder.CompetitionViewHolder;
import com.xuexiang.xui.adapter.simple.ViewHolder;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.layout.XUILinearLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CompetitionAdapter extends BaseQuickAdapter<Competition, CompetitionViewHolder> {
    public CompetitionAdapter(int layoutResId, @Nullable List<Competition> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull CompetitionViewHolder competitionViewHolder, Competition competition) {
        competitionViewHolder.setImageResource(R.id.competition_img, competition.getImageResourceId());
    }

}
