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
import com.example.competition.Database.Dao.UserDao;
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
    private int selected = 0;

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
            selected = -1;
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
                            newReply(selected == -1 ? null : adapter.getItem(selected).getUserId(),
                                    content);
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

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            String userId = this.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
            String thisUserId = this.adapter.getItem(position).getUserId();
            boolean flag = userId.equals("") || !userId.equals(thisUserId);
            switch (view.getId()) {
                case R.id.discuss_actions_btn:
                    selected = position;
                    if (flag) {
                        discussPopup1.showDown(view);
                    } else {
                        discussPopup2.showDown(view);
                    }
                    break;
                case R.id.reply_actions_btn:
                    selected = position;
                    if (flag) {
                        replyPopup1.showDown(view);
                    } else {
                        replyPopup2.showDown(view);
                    }
                    break;
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

    private void newReply(String replyUserId, String content) {
        new Thread(() -> {
            String userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
            if (userId.equals("")) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                return;
            }
            String replyId = CompetitionDao.newReply(discussId, content, userId, replyUserId);
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
            switch (item.getTitle().toString()) {
                case "收藏":
                    addFavoriteReply(this.adapter.getItem(selected).getReplyId());
                    break;
            }
        });

        discussPopup2 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("删除", null)
        }).create((adapter, item, position) -> {
            switch (item.getTitle().toString()) {
                case "收藏":
                    addFavoriteReply(this.adapter.getItem(selected).getReplyId());
                    break;
                case "删除":
                    deleteDiscuss(this.adapter.getItem(selected).getUserId(),
                            this.adapter.getItem(selected).getDiscussId());
                    break;
            }
        });

        replyPopup1 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("回复", null)
        }).create((adapter, item, position) -> {
            switch (item.getTitle().toString()) {
                case "收藏":
                    addFavoriteReply(this.adapter.getItem(selected).getReplyId());
                    break;
                case "回复":
                    selected = position;
                    dialogPlus.show();
                    break;
            }
        });

        replyPopup2 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("回复", null),
                new AdapterItem("删除", null)
        }).create((adapter, item, position) -> {
            switch (item.getTitle().toString()) {
                case "收藏":
                    addFavoriteReply(this.adapter.getItem(selected).getReplyId());
                    break;
                case "删除":
                    deleteReply(this.adapter.getItem(selected).getUserId(),
                            this.adapter.getItem(selected).getReplyId());
                    break;
                case "回复":
                    selected = position;
                    dialogPlus.show();
                    break;
            }
        });

    }

    private void addFavoriteReply(String replyId) {
        String userId = this.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
        if (userId.equals("")) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }

        new Thread(() -> {
            String favReplyId = UserDao.addFavoriteReply(userId, replyId);
            if (favReplyId == null) {
                mainHandler.post(() -> {
                    Toast.makeText(this, "收藏失败，您已收藏过该讨论", Toast.LENGTH_SHORT).show();
                });
            } else {
                mainHandler.post(() -> {
                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void deleteDiscuss(String thisUserId, String discussId) {
        String userId = this.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
        if (userId.equals("") || !userId.equals(thisUserId)) {
            Toast.makeText(this, "错误，用户不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(() -> {
            int iRow = CompetitionDao.deleteDiscuss(discussId);
            if (iRow == 0) {
                mainHandler.post(() -> {
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                });
            } else {
                mainHandler.post(() -> {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                    finishActivity();
                });
            }
        }).start();
    }

    private void deleteReply(String thisUserId, String replyId) {
        String userId = this.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
        if  (userId.equals("") || !userId.equals(thisUserId)) {
            Toast.makeText(this, "错误，用户不一致", Toast.LENGTH_SHORT).show();
        }

        new Thread(() -> {
            int iRow = CompetitionDao.deleteReply(replyId);
            if (iRow == 0) {
                mainHandler.post(() -> {
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                });
            } else {
                mainHandler.post(() -> {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                    loadMore(true);
                });
            }
        }).start();
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