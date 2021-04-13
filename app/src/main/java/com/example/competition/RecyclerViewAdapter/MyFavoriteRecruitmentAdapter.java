package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.competition.Model.MyFavoriteRecruitment;
import com.example.competition.RecyclerViewHolder.MyFavoriteRecruitmentViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MyFavoriteRecruitmentAdapter extends BaseQuickAdapter<MyFavoriteRecruitment, MyFavoriteRecruitmentViewHolder> {
    public MyFavoriteRecruitmentAdapter(int layoutResId, @Nullable List<MyFavoriteRecruitment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull MyFavoriteRecruitmentViewHolder myFavoriteRecruitmentViewHolder, MyFavoriteRecruitment myFavoriteRecruitment) {

    }
}
