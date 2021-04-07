package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Model.DiscussReply;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscussReplyAdapter extends BaseQuickAdapter<DiscussReply, BaseViewHolder> {
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DiscussReply discussReply) {

    }

    public DiscussReplyAdapter(int layoutResId, @Nullable List<DiscussReply> data) {
        super(layoutResId, data);
    }
}
