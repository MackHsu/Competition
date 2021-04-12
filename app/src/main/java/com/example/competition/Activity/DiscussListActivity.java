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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.competition.Model.Discuss;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.DiscussListAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discuss_list);

        initTestData();
        initPopup();
        initRecycler();

        binding.discussListTitlebar.setLeftClickListener(view -> {
            finish();
        });

        binding.discussListAddDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApp.getContext(), NewDiscussActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DiscussListAdapter adapter = new DiscussListAdapter(R.layout.layout_discuss_list_item, discusses);
        binding.discussListRecycler.setLayoutManager(manager);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(MyApp.getContext(), DiscussActivity.class);
                startActivity(intent);
            }
        });

        adapter.addChildClickViewIds(R.id.discuss_list_item_action_btn);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                popup1.showDown(view);
            }
        });

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
}