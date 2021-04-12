package com.example.competition.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.competition.Model.MyRecruitment;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.MyRecruitmentAdapter;
import com.example.competition.databinding.ActivityMyRecruitmentBinding;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.List;

public class MyRecruitmentActivity extends AppCompatActivity {

    private String TAG = "MyRecruitmentActivity";
    private ActivityMyRecruitmentBinding binding;
    private List<MyRecruitment> recruitments = new ArrayList<>();
    private XUISimplePopup popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_recruitment);

        binding.myRecruitmentListTitlebar.setLeftClickListener((view -> {
            finish();
        }));

        initPopup();
        initTestData();
        initRecycler();
    }

    private void initTestData() {
        for (int i = 0; i < 10; i++) {
            recruitments.add(new MyRecruitment());
        }
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.myRecruitmentRecycler.setLayoutManager(manager);
        MyRecruitmentAdapter adapter = new MyRecruitmentAdapter(R.layout.layout_my_recruitment_item, recruitments);

        adapter.addChildClickViewIds(R.id.my_recruitment_competition_name_layout, R.id.my_recruitment_action_btn);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.my_recruitment_competition_name_layout:
                    Intent intent = new Intent(MyApp.getContext(), CompetitionDetailActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_recruitment_action_btn:
                    popup.showDown(view);
                    break;
            }
        });

        binding.myRecruitmentRecycler.setAdapter(adapter);
    }

    private void initPopup() {
        popup = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("删除", null)
        })
                .create((adapter, item, position) -> {
                    Log.d(TAG, "You clicked " + position + ", " + item.getTitle().toString());
                });
    }
}