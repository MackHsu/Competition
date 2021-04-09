package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.competition.Model.TeamMemberRecruitment;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.TeamMemberRecruitmentAdapter;
import com.example.competition.databinding.ActivityTeamUpBinding;

import java.util.ArrayList;
import java.util.List;

public class TeamUpActivity extends AppCompatActivity {

    private ActivityTeamUpBinding binding;
    private List<TeamMemberRecruitment> recruitments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_up);

        initTestData();
        initRecycler();

        binding.teamUpTitlebar.setLeftClickListener(view -> {
            finish();
        });
    }

    private void initTestData() {
        for (int i = 0; i < 10; i++) {
            recruitments.add(new TeamMemberRecruitment());
        }
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.teamUpRecycler.setLayoutManager(manager);
        TeamMemberRecruitmentAdapter adapter = new TeamMemberRecruitmentAdapter(R.layout.layout_team_member_recruitment_item, recruitments);
        binding.teamUpRecycler.setAdapter(adapter);
    }
}