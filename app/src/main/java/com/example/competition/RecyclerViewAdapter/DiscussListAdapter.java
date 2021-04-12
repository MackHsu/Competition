package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.competition.Model.Discuss;
import com.example.competition.RecyclerViewHolder.DiscussListViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscussListAdapter extends BaseQuickAdapter<Discuss, DiscussListViewHolder> {
    public DiscussListAdapter(int layoutResId, @Nullable List<Discuss> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull DiscussListViewHolder discussListViewHolder, Discuss discuss) {

    }
}
