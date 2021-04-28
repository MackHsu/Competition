package com.example.competition.RecyclerViewAdapter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Database.Dao.UserDao;
import com.example.competition.Model.Discuss;
import com.example.competition.R;
import com.example.competition.RecyclerViewHolder.DiscussListViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscussListAdapter extends BaseQuickAdapter<Discuss, DiscussListViewHolder> implements LoadMoreModule {
    private Handler mainHandler;

    public DiscussListAdapter(int layoutResId) {
        super(layoutResId);
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void convert(@NotNull DiscussListViewHolder discussListViewHolder, Discuss discuss) {
        discussListViewHolder.setText(R.id.discuss_list_item_title, discuss.getTitle());
        discussListViewHolder.setText(R.id.discuss_list_item_time, discuss.getDate());
        new Thread(() -> {
            String userName = UserDao.getUserName(discuss.getUserId());
            String content = CompetitionDao.getDiscussContent(discuss.getDiscussId());
            mainHandler.post(() -> {
               discussListViewHolder.setText(R.id.discuss_list_item_username, userName);
               discussListViewHolder.setText(R.id.discuss_list_item_content, content);
            });
        }).start();
    }
}
