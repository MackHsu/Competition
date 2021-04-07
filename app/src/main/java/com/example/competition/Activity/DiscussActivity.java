package com.example.competition.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.competition.Model.DiscussReply;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.DiscussReplyAdapter;
import com.example.competition.databinding.ActivityDiscussBinding;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.List;

public class DiscussActivity extends AppCompatActivity {

    private ActivityDiscussBinding binding;
    List<DiscussReply> replies = new ArrayList<>();
    private XUISimplePopup discussPopup1;
    private XUISimplePopup discussPopup2;
    private XUISimplePopup replyPopup1;
    private XUISimplePopup replyPopup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discuss);

        binding.discussTitlebar.setLeftClickListener(view -> {
            finish();
        });

        initMenuPopup();
        initTestData();
        initRecycler();

    }

    private void initRecycler() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.discussReplyRecycler.setLayoutManager(layoutManager);
        DiscussReplyAdapter adapter = new DiscussReplyAdapter(replies);
//        adapter.setAnimationEnable(true);
//        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
//        adapter.setAnimationFirstOnly(false);

        adapter.addChildClickViewIds(R.id.reply_actions_btn, R.id.discuss_actions_btn);

        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.discuss_actions_btn:
                        discussPopup1.showDown(view);
                        break;
                    case R.id.reply_actions_btn:
                        replyPopup1.showDown(view);
                        break;
                }
            }
        });

        binding.discussReplyRecycler.setAdapter(adapter);
    }

    private void initTestData() {
        replies.add(new DiscussReply());
        for (int i = 0; i < 50; i++) {
            DiscussReply temp = new DiscussReply();
            temp.setItemType(DiscussReply.REPLY);
            replies.add(temp);
        }
    }

    private void initMenuPopup() {
        discussPopup1 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null)
        }).create((adapter, item, position) -> {
            Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        });

        discussPopup2 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("删除", null)
        }).create((adapter, item, position) -> {
            Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        });

        replyPopup1 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("回复", null)
        }).create((adapter, item, position) -> {
            Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        });

        replyPopup2 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("回复", null),
                new AdapterItem("删除", null)
        }).create((adapter, item, position) -> {
            Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        });

    }
}