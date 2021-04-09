package com.example.competition.RecyclerViewAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Model.TeamMemberRecruitment;
import com.example.competition.RecyclerViewHolder.TeamMemberRecruitmentViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeamMemberRecruitmentAdapter extends BaseQuickAdapter<TeamMemberRecruitment, TeamMemberRecruitmentViewHolder> {
    public TeamMemberRecruitmentAdapter(int layoutResId, @Nullable List<TeamMemberRecruitment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull TeamMemberRecruitmentViewHolder teamMemberRecruitmentViewHolder, TeamMemberRecruitment teamMemberRecruitment) {

    }
}
