package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Database.Model.Competition;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.databinding.ActivityCompetitionDetailBinding;

import java.text.SimpleDateFormat;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_competition_detail);

        setOnClickListener();
        getData();


    }

    private void getData() {
        new Thread(() -> {
            Bitmap img = CompetitionDao.getImg(competitionId);
            Competition competitionDetail = CompetitionDao.getCompetitionDetail(competitionId);
            String typeName = CompetitionDao.getTypeName(competitionDetail.getTypeId());
            String levelName = CompetitionDao.getLevelName(competitionDetail.getLevelId());
            mainHandler.post(() -> {
                binding.competitionDetailImg.setImageBitmap(img);
                binding.competitionDetailName.setText(competitionDetail.getName());

                String signUpDateStr1 = SimpleDateFormat.getDateTimeInstance().format(competitionDetail.getSignUpDate1());
                String signUpDateStr2 = SimpleDateFormat.getDateTimeInstance().format(competitionDetail.getSignUpDate2());
                String competitionDateStr1 = SimpleDateFormat.getDateTimeInstance().format(competitionDetail.getCompetitionDate1());
                String competitionDateStr2 = SimpleDateFormat.getDateTimeInstance().format(competitionDetail.getCompetitionDate2());
                binding.competitionDetailSignupTime.setText("报名时间：" + signUpDateStr1 + " - " + signUpDateStr2);
                binding.competitionDetailTime.setText("比赛时间：" + competitionDateStr1 + " - " + competitionDateStr2);

                binding.competitionDetailHost.setText(competitionDetail.getHost());
                binding.competitionDetailDesc.setText(competitionDetail.getDescription());
                binding.competitionDetailType.setText(typeName);
                binding.competitionDetailLevel.setText(levelName);
            });
        }).start();
    }

    private void setOnClickListener() {
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
            intent.putExtra("competitionId", competitionId);
            startActivity(intent);
        });

        binding.competitionDetailDiscuss.setOnClickListener(view -> {
            binding.competitionDetailFloatingMenu.close(true);
            Intent intent = new Intent(MyApp.getContext(), DiscussListActivity.class);
            intent.putExtra("competitionId", competitionId);
            startActivity(intent);
        });

        binding.competitionDetailTestBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MyApp.getContext(), TestUpdateImgActivity.class);
            intent.putExtra("competition_id", competitionId);
            startActivity(intent);
        });
    }
}