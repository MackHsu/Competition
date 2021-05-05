package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Model.Message;
import com.example.competition.R;

import org.jetbrains.annotations.NotNull;

public class MessageListAdapter extends BaseMultiItemQuickAdapter<Message, BaseViewHolder> implements UpFetchModule {
    public MessageListAdapter() {
        addItemType(Message.TYPE_SEND, R.layout.layout_message_send_item);
        addItemType(Message.TYPE_RECEIVE, R.layout.layout_message_receive_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Message message) {
        switch (message.getItemType()) {
            case Message.TYPE_SEND:
                baseViewHolder.setText(R.id.message_send_text, message.getContent());
                baseViewHolder.setText(R.id.message_send_date, message.getDate());
                break;
            case Message.TYPE_RECEIVE:
                baseViewHolder.setText(R.id.message_receive_text, message.getContent());
                baseViewHolder.setText(R.id.message_receive_date, message.getDate());
                break;
        }
    }
}
