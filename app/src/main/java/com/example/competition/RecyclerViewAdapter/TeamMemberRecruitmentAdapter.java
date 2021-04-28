package com.example.competition.RecyclerViewAdapter;

import android.os.Handler;
import android.os.Looper;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.competition.Database.Dao.UserDao;
import com.example.competition.Model.Recruitment;
import com.example.competition.R;
import com.example.competition.RecyclerViewHolder.TeamMemberRecruitmentViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeamMemberRecruitmentAdapter extends BaseQuickAdapter<Recruitment, TeamMemberRecruitmentViewHolder> implements LoadMoreModule {

    private Handler mainHandler;

    public TeamMemberRecruitmentAdapter(int layoutResId) {
        super(layoutResId);
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void convert(@NotNull TeamMemberRecruitmentViewHolder teamMemberRecruitmentViewHolder, Recruitment teamMemberRecruitment) {
        teamMemberRecruitmentViewHolder.setText(R.id.team_time, teamMemberRecruitment.getDate());
        teamMemberRecruitmentViewHolder.setText(R.id.team_desc, teamMemberRecruitment.getContent());
        new Thread(() -> {
            String userName = UserDao.getUserName(teamMemberRecruitment.getUserId());
            mainHandler.post(() -> teamMemberRecruitmentViewHolder.setText(R.id.team_username, userName));
        }).start();
    }
}
