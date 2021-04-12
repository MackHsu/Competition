package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.competition.Model.MyRecruitment;
import com.example.competition.RecyclerViewHolder.MyRecruitmentViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MyRecruitmentAdapter extends BaseQuickAdapter<MyRecruitment, MyRecruitmentViewHolder> {
    public MyRecruitmentAdapter(int layoutResId, @Nullable List<MyRecruitment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull MyRecruitmentViewHolder myRecruitmentViewHolder, MyRecruitment myRecruitment) {

    }
}
