package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.competition.Model.MyFavoriteDiscussReply;
import com.example.competition.RecyclerViewHolder.MyFavoriteDiscussReplyViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MyFavoriteDiscussReplyAdapter extends BaseQuickAdapter<MyFavoriteDiscussReply, MyFavoriteDiscussReplyViewHolder> {
    public MyFavoriteDiscussReplyAdapter(int layoutResId, @Nullable List<MyFavoriteDiscussReply> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull MyFavoriteDiscussReplyViewHolder myFavoriteDiscussReplyViewHolder, MyFavoriteDiscussReply myFavoriteDiscussReply) {

    }
}
