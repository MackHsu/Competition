package com.example.competition.Activity;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.competition.Model.TeamMemberRecruitment;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.TeamMemberRecruitmentAdapter;
import com.example.competition.databinding.ActivityTeamUpBinding;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.List;

public class TeamUpActivity extends AppCompatActivity {

    private String TAG = "TeamUpActivity";
    private ActivityTeamUpBinding binding;
    private List<TeamMemberRecruitment> recruitments = new ArrayList<>();
    private XUISimplePopup popup1;
    private XUISimplePopup popup2;
    private DialogPlus dialogPlus;
    private MaterialDialog.Builder materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_up);

        initTestData();
        initPopup();
        initRecycler();
        initDialog();

        binding.teamUpTitlebar.setLeftClickListener(view -> {
            finish();
        });

        binding.teamUpAddRecruitment.setOnClickListener(view -> {
            dialogPlus.show();
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

        adapter.addChildClickViewIds(R.id.team_action_btn);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                popup1.showDown(view);
            }
        });

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
        dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.layout_new_recruitment_dialog))
                .setFooter(R.layout.layout_new_recruitment_footer)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setOnClickListener((dialog, view) -> {
                    switch (view.getId()) {
                        case R.id.new_recruitment_cancel_btn:
                            dialog.dismiss();
                            break;
                        case R.id.new_recruitment_confirm_btn:
                            Log.d(TAG, "onClick: confirm");
                            dialog.dismiss();
                            break;
                    }
                })
                .create();
    }
}