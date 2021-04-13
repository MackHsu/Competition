package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.competition.Fragment.MyFavoriteDiscussReplyFragment;
import com.example.competition.Fragment.MyFavoriteRecruitmentFragment;
import com.example.competition.R;
import com.example.competition.databinding.ActivityMyFavoriteBinding;
import com.xuexiang.xui.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFavoriteActivity extends AppCompatActivity {

    private ActivityMyFavoriteBinding binding;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_favorite);

        binding.myFavoriteTitlebar.setLeftClickListener((view) -> {
            finish();
        });

        fragments.add(new MyFavoriteDiscussReplyFragment());
        fragments.add(new MyFavoriteRecruitmentFragment());
        String[] titles = new String[]{"讨论", "组队"};

        binding.myFavoriteIndicator.setTabTitles(titles);
        binding.myFavoriteIndicator.setViewPager(binding.myFavoriteViewPager, new FragmentAdapter<>(getSupportFragmentManager(), fragments));
        binding.myFavoriteViewPager.setOffscreenPageLimit(fragments.size() - 1);
    }
}