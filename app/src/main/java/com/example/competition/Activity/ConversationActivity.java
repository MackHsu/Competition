package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Database.Dao.ConversationDao;
import com.example.competition.Database.Dao.UserDao;
import com.example.competition.Model.Discuss;
import com.example.competition.Model.Message;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.MessageListAdapter;
import com.example.competition.databinding.ActivityConversationBinding;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    private String TAG = "ConversationActivity";
    private ActivityConversationBinding binding;
    private String user2Id;
    private Handler mainHandler;
    private MessageListAdapter adapter;
    private int pageSize = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_conversation);
        user2Id = getIntent().getStringExtra("user2Id");
        mainHandler = new Handler(getMainLooper());

        binding.conversationTitleBar.setLeftClickListener(view -> finish());

        new Thread(() -> {
            String user2Name = UserDao.getUserName(user2Id);
            mainHandler.post(() -> binding.conversationTitleBar.setTitle(user2Name));
        }).start();

        initRecycler();

        binding.sendMessageBtn.setOnClickListener(view -> {
            if (!inputCheck()) return;
            String userId = this.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
            if (userId.equals("")) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                return;
            }
            new Thread(() -> {
                String ret = ConversationDao.addMessage(userId, user2Id, binding.messageInput.getEditValue());
                mainHandler.post(() -> {
                    Log.d(TAG, "onCreate: " + ret);
                    binding.messageInput.clear();
                    loadMore(true);
                });
            }).start();
        });

        loadMore(true);
    }

    private boolean inputCheck() {
        String input = binding.messageInput.getEditValue();
        Log.d(TAG, "inputCheck: input: " + input);
        if (input == null || input.equals("")) {
            binding.messageInput.setError("内容不能为空");
            return false;
        }
        return true;
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.messageListRecycler.setLayoutManager(manager);
        adapter = new MessageListAdapter();
        adapter.getUpFetchModule().setOnUpFetchListener(() -> loadMore(false));
        adapter.getUpFetchModule().setUpFetchEnable(true);
        adapter.getUpFetchModule().setStartUpFetchPosition(1);
        binding.messageListRecycler.setAdapter(adapter);
    }

    private void loadMore(boolean isNew) {
        new Thread(() -> {
            adapter.getUpFetchModule().setUpFetching(true);
            int start = isNew ? 0 : adapter.getItemCount();
            String userId = this.getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", "");
            List<Message> list = ConversationDao.getMessageListWithUserId(userId, user2Id, start, pageSize);
            mainHandler.post(() -> {
                if (list.size() == 0) {
                    adapter.getUpFetchModule().setUpFetching(true);
                    return;
                }

                if (adapter.getItemCount() == 0 || isNew) {
                    adapter.setList(list);
                } else {
                    adapter.addData(0, list);
                }

                if (isNew) binding.messageListRecycler.scrollToPosition(adapter.getItemCount() - 1);
                adapter.getUpFetchModule().setUpFetching(true);
            });
        }).start();
    }
}