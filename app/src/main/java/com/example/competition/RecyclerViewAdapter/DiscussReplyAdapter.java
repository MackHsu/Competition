package com.example.competition.RecyclerViewAdapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.competition.Model.DiscussReply;
import com.example.competition.R;
import com.example.competition.RecyclerViewHolder.DiscussReplyViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscussReplyAdapter extends BaseMultiItemQuickAdapter<DiscussReply, DiscussReplyViewHolder> {

    public DiscussReplyAdapter(@Nullable List<DiscussReply> data) {
        super(data);
        addItemType(DiscussReply.DISCUSS, R.layout.layout_discuss_item);
        addItemType(DiscussReply.REPLY, R.layout.layout_discuss_reply_item);
    }

    @Override
    protected void convert(@NotNull DiscussReplyViewHolder discussReplyViewHolder, DiscussReply discussReply) {
        Log.d("DiscussReplyAdapter", "convert: ");
        int itemType = discussReply.getItemType();
        discussReplyViewHolder.setXuiLinearLayout(itemType);
        discussReplyViewHolder.setRadiusAndShadow();
    }
}
