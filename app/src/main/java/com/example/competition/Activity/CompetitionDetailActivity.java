package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.DiscussListAdapter;
import com.example.competition.databinding.ActivityCompetitionDetailBinding;
import com.xuexiang.xui.widget.popupwindow.good.GoodView;
import com.xuexiang.xui.widget.popupwindow.good.IGoodView;

import java.sql.Blob;

public class CompetitionDetailActivity extends AppCompatActivity {

    private String TAG = "CompetitionDetailActivity";
    private ActivityCompetitionDetailBinding binding;
    private boolean fav = false;
    private String competitionId;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        competitionId = getIntent().getStringExtra("competition_id");
        mainHandler = new Handler(Looper.getMainLooper());
        Log.d(TAG, "onCreate: competition id: " + competitionId);
//        setContentView(R.layout.activity_competition_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_competition_detail);

        binding.competitionDetailTitlebar.setLeftClickListener(view -> {
            finish();
        });

        binding.competitionDetailFav.setOnClickListener(view -> {
            if (fav) {
                binding.competitionDetailFav.setImageResource(R.drawable.ic_favorite_4);
                binding.competitionDetailFav.setLabelText("添加收藏");
            } else {
                binding.competitionDetailFav.setImageResource(R.drawable.ic_favorite_3);
                binding.competitionDetailFav.setLabelText("取消收藏");
            }
            fav = !fav;
        });

        binding.competitionDetailTeam.setOnClickListener(view -> {
            binding.competitionDetailFloatingMenu.close(true);
            Intent intent = new Intent(MyApp.getContext(), TeamUpActivity.class);
            startActivity(intent);
        });

        binding.competitionDetailDiscuss.setOnClickListener(view -> {
            binding.competitionDetailFloatingMenu.close(true);
            Intent intent = new Intent(MyApp.getContext(), DiscussListActivity.class);
            startActivity(intent);
        });

        binding.competitionDetailTestBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MyApp.getContext(), TestUpdateImgActivity.class);
            intent.putExtra("competition_id", competitionId);
            startActivity(intent);
        });

        new Thread(() -> {
            Bitmap img = CompetitionDao.getImg(competitionId);
            mainHandler.post(() -> binding.competitionDetailImg.setImageBitmap(img));
        }).start();
    }
}