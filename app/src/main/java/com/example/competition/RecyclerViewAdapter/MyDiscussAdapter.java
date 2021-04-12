package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.competition.Model.MyDiscuss;
import com.example.competition.RecyclerViewHolder.MyDiscussViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MyDiscussAdapter extends BaseQuickAdapter<MyDiscuss, MyDiscussViewHolder> {

    public MyDiscussAdapter(int layoutResId, @Nullable List<MyDiscuss> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull MyDiscussViewHolder myDiscussViewHolder, MyDiscuss myDiscuss) {

    }
}
