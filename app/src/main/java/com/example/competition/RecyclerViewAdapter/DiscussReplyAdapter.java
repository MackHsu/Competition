package com.example.competition.RecyclerViewAdapter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Database.Dao.UserDao;
import com.example.competition.Model.DiscussReply;
import com.example.competition.R;
import com.example.competition.RecyclerViewHolder.DiscussReplyViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscussReplyAdapter extends BaseMultiItemQuickAdapter<DiscussReply, DiscussReplyViewHolder> implements LoadMoreModule {
    private Handler mainHandler;

    public DiscussReplyAdapter(@Nullable List<DiscussReply> data) {
        super(data);
        mainHandler = new Handler(Looper.getMainLooper());
        addItemType(DiscussReply.DISCUSS, R.layout.layout_discuss_item);
        addItemType(DiscussReply.REPLY, R.layout.layout_discuss_reply_item);
    }

    @Override
    protected void convert(@NotNull DiscussReplyViewHolder discussReplyViewHolder, DiscussReply discussReply) {
        int itemType = discussReply.getItemType();
        discussReplyViewHolder.setXuiLinearLayout(itemType);
        discussReplyViewHolder.setRadiusAndShadow();
        switch (itemType) {
            case DiscussReply.DISCUSS:
                discussReplyViewHolder.setText(R.id.discuss_time, discussReply.getDate());
                discussReplyViewHolder.setText(R.id.discuss_reply_count, "");
                discussReplyViewHolder.setText(R.id.discuss_content, discussReply.getContent());
                new Thread(() -> {
                    String userName = UserDao.getUserName(discussReply.getUserId());
                    String title = CompetitionDao.getDiscussTitle(discussReply.getDiscussId());
                    mainHandler.post(() -> {
                        discussReplyViewHolder.setText(R.id.discuss_username, userName);
                        discussReplyViewHolder.setText(R.id.discuss_title, title);
                    });
                }).start();
                break;
            case DiscussReply.REPLY:
                discussReplyViewHolder.setText(R.id.reply_content, discussReply.getContent());
                discussReplyViewHolder.setText(R.id.reply_index, "");
                discussReplyViewHolder.setText(R.id.reply_time, discussReply.getDate());
                discussReplyViewHolder.setText(R.id.reply_reply_text, "");
                new Thread(() -> {
                    String userName = UserDao.getUserName(discussReply.getUserId());
                    String replyUserId = discussReply.getReplyUserId();
                    String replyUserName = replyUserId == null ? null : UserDao.getUserName(discussReply.getReplyUserId());
                    mainHandler.post(() -> {
                        discussReplyViewHolder.setText(R.id.reply_username, userName);
                        discussReplyViewHolder.setText(R.id.reply_reply_text, replyUserName == null ? "" : "回复 " + replyUserName + ":");
                    });
                }).start();
                break;
        }
    }
}
