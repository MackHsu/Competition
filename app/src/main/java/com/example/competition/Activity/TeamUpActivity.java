package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Model.Recruitment;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.TeamMemberRecruitmentAdapter;
import com.example.competition.databinding.ActivityTeamUpBinding;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.List;

public class TeamUpActivity extends AppCompatActivity {

    private String TAG = "TeamUpActivity";
    private ActivityTeamUpBinding binding;
    private List<Recruitment> recruitments = new ArrayList<>();
    private XUISimplePopup popup1;
    private XUISimplePopup popup2;
    private DialogPlus dialogPlus;
    private MaterialDialog.Builder materialDialog;
    private String competitionId;
    private Handler mainHandler;
    private TeamMemberRecruitmentAdapter adapter;

    private static int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_up);
        mainHandler = new Handler(getMainLooper());
        competitionId = getIntent().getStringExtra("competitionId");
        Log.d(TAG, "onCreate: competitionId: " + competitionId);

        initPopup();
        initRecycler();
        initDialog();

        loadMore();

        binding.teamUpTitlebar.setLeftClickListener(view -> {
            finish();
        });

        binding.teamUpAddRecruitment.setOnClickListener(view -> {
            String userId = this.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
            if (userId.equals("")) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                return;
            }
            dialogPlus.show();
        });
    }

    private void initTestData() {
        for (int i = 0; i < 10; i++) {
            recruitments.add(new Recruitment());
        }
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.teamUpRecycler.setLayoutManager(manager);
        adapter = new TeamMemberRecruitmentAdapter(R.layout.layout_team_member_recruitment_item);
        adapter.getLoadMoreModule().setOnLoadMoreListener(this::loadMore);
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setAutoLoadMore(true);
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);

        adapter.addChildClickViewIds(R.id.team_action_btn);
        adapter.setOnItemChildClickListener((adapter, view, position) -> popup1.showDown(view));

        binding.teamUpRecycler.setAdapter(adapter);
    }

    private void initPopup() {
        popup1 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null)
        })
            .create((adapter, item, position) -> {
                Log.d(TAG, "You clicked " + position + ", " + item.getTitle().toString());
            });

        popup1 = new XUISimplePopup(this, new AdapterItem[] {
                new AdapterItem("收藏", null),
                new AdapterItem("删除", null)
        })
                .create((adapter, item, position) -> {
                    Log.d(TAG, "You clicked " + position + ", " + item.getTitle().toString());
                });
    }

    private void initDialog() {
        TeamUpActivity that = this;
        dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_new_recruitment_dialog))
                .setFooter(R.layout.layout_new_recruitment_footer)
                .setHeader(R.layout.layout_new_recruitment_header)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOnClickListener((dialog, view) -> {
                    switch (view.getId()) {
                        case R.id.new_recruitment_cancel_btn:
                            dialog.dismiss();
                            break;
                        case R.id.new_recruitment_confirm_btn:
                            Log.d(TAG, "onClick: confirm");
                            String userId = that.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
                            if (userId.equals("")) {
                                Toast.makeText(that, "请先登录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(that, SignUpActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                                return;
                            }
                            MultiLineEditText editText = dialog.getHolderView().findViewById(R.id.team_recruitment_desc);
                            String content = editText.getContentText();
                            Log.d(TAG, "initDialog: content" + content);
                            new Thread(() -> {
                                int iRow = CompetitionDao.newRecruitment(competitionId, userId, content);
                                if (iRow > 0) {
                                    mainHandler.post(() -> {
                                        Toast.makeText(that, "发布成功", Toast.LENGTH_SHORT).show();
                                        adapter.getLoadMoreModule().setEnableLoadMore(true);
                                        dialog.dismiss();
                                        finish();
                                        Intent intent = new Intent(TeamUpActivity.this, TeamUpActivity.class);
                                        intent.putExtra("competitionId", competitionId);
                                        startActivity(intent);
                                    });
                                }
                            }).start();
                            break;
                    }
                })
                .create();
    }

    private void loadMore() {
        new Thread(() -> {
            List<Recruitment> list = CompetitionDao.getRecruitmentList(competitionId, adapter.getItemCount(), pageSize);
            mainHandler.post(() -> {
                if (list.size() == 0) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                    return;
                }
                adapter.getLoadMoreModule().loadMoreComplete();
                if (adapter.getItemCount() == 0) {
                    adapter.setList(list);
                } else {
                    adapter.addData(list);
                }
            });
        }).start();
    }
}