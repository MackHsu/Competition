package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Model.Conversation;
import com.example.competition.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ConversationAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {

    public ConversationAdapter(int layoutResId, @Nullable List<Conversation> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Conversation conversation) {
        baseViewHolder.setText(R.id.conversation_name, conversation.getName());
    }
}
