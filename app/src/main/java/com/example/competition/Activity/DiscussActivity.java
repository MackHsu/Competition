package com.example.competition.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Model.Discuss;
import com.example.competition.Model.DiscussReply;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.DiscussReplyAdapter;
import com.example.competition.databinding.ActivityDiscussBinding;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.List;

public class DiscussActivity extends AppCompatActivity {

    private String TAG = "DiscussActivity";
    private ActivityDiscussBinding binding;
    List<DiscussReply> replies = new ArrayList<>();
    private XUISimplePopup discussPopup1;
    private XUISimplePopup discussPopup2;
    private XUISimplePopup replyPopup1;
    private XUISimplePopup replyPopup2;
    private DialogPlus dialogPlus;
    private Handler mainHandler;
    private DiscussReplyAdapter adapter;
    private String discussId;
    private String competitionId;
    private int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discuss);
        mainHandler = new Handler(getMainLooper());
        discussId = getIntent().getStringExtra("discussId");
        competitionId = getIntent().getStringExtra("competitionId");

        binding.discussTitlebar.setLeftClickListener(view -> {
            finish();
        });

        initMenuPopup();
//        initTestData();
        initRecycler();
        initDialog();

        binding.discussAddReply.setOnClickListener((view -> {
            dialogPlus.show();
        }));

        loadMore(true);
    }

    private void initDialog() {
        dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_new_reply_dialog))
                .setFooter(R.layout.layout_new_recruitment_footer)
                .setHeader(R.layout.layout_new_reply_header)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOnClickListener((dialog, view) -> {
                    switch (view.getId()) {
                        case R.id.new_recruitment_cancel_btn:
                            dialog.dismiss();
                            break;
                        case R.id.new_recruitment_confirm_btn:
                            MultiLineEditText editText = dialog.getHolderView().findViewById(R.id.new_reply_content);
                            String content = editText.getContentText();
                            newReply(content);
                            break;
                    }
                })
                .create();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.discussReplyRecycler.setLayoutManager(layoutManager);
        adapter = new DiscussReplyAdapter(replies);
        adapter.addChildClickViewIds(R.id.reply_actions_btn, R.id.discuss_actions_btn);
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> loadMore(false));
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setAutoLoadMore(true);
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);

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

    private void loadMore(boolean isNew) {
        new Thread(() -> {
            int start = isNew ? 0 : adapter.getItemCount();
            List<DiscussReply> list = CompetitionDao.getReplyList(discussId, start, pageSize);
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

    private void newReply(String content) {
        new Thread(() -> {
            String userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
            if (userId.equals("")) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                return;
            }
            String replyId = CompetitionDao.newReply(discussId, content, userId, null);
            if (replyId != null) {
                mainHandler.post(() -> {
                    Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                    dialogPlus.dismiss();
                    loadMore(true);
                });
            }
        }).start();
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

    private void finishActivity() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }
}