package com.example.competition.RecyclerViewAdapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Model.Competition;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.RecyclerViewHolder.CompetitionViewHolder;
import com.xuexiang.xui.adapter.simple.ViewHolder;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.layout.XUILinearLayout;

import org.jetbrains.annotations.NotNull;

public class CompetitionAdapter extends BaseQuickAdapter<Competition, CompetitionViewHolder> implements LoadMoreModule {
    private Handler mainHandler;

    public CompetitionAdapter(int layoutResId) {
        super(layoutResId);
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void convert(@NotNull CompetitionViewHolder competitionViewHolder, Competition competition) {
        competitionViewHolder.setText(R.id.competition_name, competition.getName());
        new Thread(() -> {
            Bitmap img = CompetitionDao.getImg(competition.getId());
            mainHandler.post(() -> competitionViewHolder.setImageBitmap(R.id.competition_img, img));
        }).start();
    }

}
