package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Model.DiscussReply;
import com.example.competition.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscussReplyAdapter extends BaseMultiItemQuickAdapter<DiscussReply, BaseViewHolder> {
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DiscussReply discussReply) {

    }

    public DiscussReplyAdapter(@Nullable List<DiscussReply> data) {
        super(data);
        addItemType(DiscussReply.DISCUSS, R.layout.layout_discuss_item);
        addItemType(DiscussReply.REPLY, R.layout.layout_discuss_reply_item);
    }
}
