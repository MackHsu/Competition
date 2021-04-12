package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.competition.R;
import com.example.competition.databinding.ActivityNewDiscussBinding;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class NewDiscussActivity extends AppCompatActivity {

    private String TAG = "NewDiscussActivity";
    private ActivityNewDiscussBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_discuss);

        binding.newDiscussTitlebar.setLeftClickListener(view -> {
            finish();
        })
                .addAction(new TitleBar.TextAction("完成") {
                    @Override
                    public void performAction(View view) {
                        Log.d(TAG, "performAction: you clicked: 完成");
                        finish();
                    }
                });
    }
}