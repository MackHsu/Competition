package com.example.competition.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Model.Discuss;
import com.example.competition.Model.Recruitment;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.DiscussListAdapter;
import com.example.competition.RecyclerViewAdapter.DiscussReplyAdapter;
import com.example.competition.databinding.ActivityDiscussListBinding;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.List;

public class DiscussListActivity extends AppCompatActivity {

    private static final String TAG = "DiscussListActivity";
    private ActivityDiscussListBinding binding;
    private List<Discuss> discusses = new ArrayList<>();
    private XUISimplePopup popup1;
    private XUISimplePopup popup2;
    private String competitionId;
    private DiscussListAdapter adapter;
    private int pageSize = 20;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discuss_list);
        competitionId = getIntent().getStringExtra("competitionId");
        mainHandler = new Handler(getMainLooper());
//        initTestData();
        initPopup();
        initRecycler();

        binding.discussListTitlebar.setLeftClickListener(view -> {
            finish();
        });

        binding.discussListAddDiscuss.setOnClickListener(view -> {
            String userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
            if (userId.equals("")) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                return;
            }
            Intent intent = new Intent(MyApp.getContext(), NewDiscussActivity.class);
            intent.putExtra("competitionId", competitionId);
            startActivityForResult(intent, 1);
        });

        loadMore(false);
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new DiscussListAdapter(R.layout.layout_discuss_list_item);
        binding.discussListRecycler.setLayoutManager(manager);
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> loadMore(false));
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setAutoLoadMore(true);
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);

        adapter.setOnItemClickListener((adapter12, view, position) -> {
            Intent intent = new Intent(MyApp.getContext(), DiscussActivity.class);
            intent.putExtra("discussId", adapter.getItem(position).getDiscussId());
            intent.putExtra("competitionId", competitionId);
            startActivityForResult(intent, 1);
        });

        adapter.addChildClickViewIds(R.id.discuss_list_item_action_btn);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> popup1.showDown(view));

        binding.discussListRecycler.setAdapter(adapter);
    }

    private void initTestData() {
        for (int i = 0; i < 10; i++) {
            discusses.add(new Discuss());
        }
    }

    private void initPopup() {
        popup1 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null)
        })
                .create((adapter, item, position) -> {
                    Log.d(TAG, "You clicked " + position + ", " + item.getTitle().toString());
                });

        popup2 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("删除", null)
        })
                .create((adapter, item, position) -> {
                    Log.d(TAG, "You clicked " + position + ", " + item.getTitle().toString());
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // 进入讨论详情
                String discussId = data.getStringExtra("discussId");
                Intent intent = new Intent(this, DiscussActivity.class);
                intent.putExtra("discussId", discussId);
                intent.putExtra("competitionId", competitionId);
                startActivityForResult(intent, 1);
            } else if (resultCode == RESULT_CANCELED) {
                // 刷新讨论列表
                Log.d(TAG, "onActivityResult: loadMore");
                loadMore(true);
            }
        }
    }

    private void loadMore(Boolean isNew) {
        new Thread(() -> {
            int start = isNew ? 0 : adapter.getItemCount();
            List<Discuss> list = CompetitionDao.getDiscussList(competitionId, start, pageSize);
            mainHandler.post(() -> {
                if (list.size() == 0) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                    return;
                }
                adapter.getLoadMoreModule().loadMoreComplete();
                if (adapter.getItemCount() == 0 || isNew) {
                    adapter.setList(list);
                } else {
                    adapter.addData(list);
                }
            });
        }).start();
    }

}