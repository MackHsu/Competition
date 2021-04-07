package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.competition.Model.DiscussReply;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.CompetitionAdapter;
import com.example.competition.RecyclerViewAdapter.DiscussReplyAdapter;
import com.example.competition.databinding.ActivityDiscussBinding;

import java.util.ArrayList;
import java.util.List;

public class DiscussActivity extends AppCompatActivity {

    private ActivityDiscussBinding binding;
    List<DiscussReply> replies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discuss);

        binding.discussTitlebar.setLeftClickListener(view -> {
            finish();
        });

        initTestData();
        initRecycler();

        binding.discussScroll.post(new Runnable() {
            @Override
            public void run() {
                binding.discussScroll.scrollTo(0, 0);
            }
        });
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.discussReplyRecycler.setLayoutManager(layoutManager);
        DiscussReplyAdapter adapter = new DiscussReplyAdapter(R.layout.layout_discuss_reply_item, replies);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        adapter.setAnimationFirstOnly(false);
        binding.discussReplyRecycler.setAdapter(adapter);
    }

    private void initTestData() {
        for (int i = 0; i < 10; i++) {
            replies.add(new DiscussReply());
        }
    }

    private void initMenuPopup() {

    }
}