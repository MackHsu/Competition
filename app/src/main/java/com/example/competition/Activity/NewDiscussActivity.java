package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.databinding.ActivityNewDiscussBinding;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class NewDiscussActivity extends AppCompatActivity {

    private String TAG = "NewDiscussActivity";
    private ActivityNewDiscussBinding binding;
    private String competitionId;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_discuss);
        competitionId = getIntent().getStringExtra("competitionId");
        mainHandler = new Handler(getMainLooper());

        binding.newDiscussTitlebar.setLeftClickListener(view -> {
            finishActivity();
        })
                .addAction(new TitleBar.TextAction("完成") {
                    @Override
                    public void performAction(View view) {
                        if (!inputCheck()) return;
                        String title = binding.newDiscussTitle.getEditValue();
                        String content = binding.newDiscussContent.getContentText();
                        String userId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
                        if (userId.equals("")) {
                            Intent intent = new Intent(NewDiscussActivity.this, SignUpActivity.class);
                            Toast.makeText(NewDiscussActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            return;
                        }
                        new Thread(() -> {
                            String discussId = CompetitionDao.newDiscuss(title, competitionId, userId, content);
                            if (discussId != null) {
                                mainHandler.post(() -> {
                                    Toast.makeText(NewDiscussActivity.this, "成功发布讨论", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("discussId", discussId);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                });
                            }
                        }).start();
                    }
                });
    }

    private boolean inputCheck() {
        String title = binding.newDiscussTitle.getEditValue();
        Log.d(TAG, "inputCheck: title: " + title);
        Log.d(TAG, "inputCheck: content: " + binding.newDiscussContent.getContentText());
        if (title == null || title.equals("")) {
            binding.newDiscussTitle.setError("标题不能为空");
            return false;
        }
        return true;
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