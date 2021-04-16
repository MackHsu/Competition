package com.example.competition.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.competition.Activity.CompetitionDetailActivity;
import com.example.competition.Database.Dao.CompetitionDao;
import com.example.competition.Model.Competition;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.CompetitionAdapter;
import com.example.competition.Services.CompetitionService;
import com.example.competition.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
//    private List<Competition> competitions = new ArrayList<>();
    private CompetitionAdapter adapter;
    private Handler mainHandler;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        mainHandler = new Handler(Looper.getMainLooper());
        initRecyclerView();

        binding.competitionSearch.setOnClickListener((view -> {
            Log.d(TAG, "onCreateView: menu clicked.");
            new Thread(() -> {
                Log.d(TAG, "run: thread stated.");
                List<com.example.competition.Database.Model.Competition> list = CompetitionDao.getCompetition(null, null, null, 0, 1);

                mainHandler.post(() -> {
                    List<Competition> list2 = new ArrayList<>();
                    for (com.example.competition.Database.Model.Competition item : list) {
                        Competition competition = new Competition();
                        competition.setName(item.getName());
                        competition.setId(item.getCompetitionId());
                        list2.add(competition);
                        Log.d(TAG, "competition " + item.getName() + " added");
                    }
                    adapter.setList(list2);
                });
            }).start();
        }));

        loadMore();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.competitionRecycler.setLayoutManager(layoutManager);
        adapter = new CompetitionAdapter(R.layout.layout_competition_item);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        adapter.setAnimationFirstOnly(true);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), CompetitionDetailActivity.class);
            Competition item = (Competition) adapter.getData().get(position);
            intent.putExtra("competition_id", item.getId());
            startActivity(intent);
        });

        adapter.getLoadMoreModule().setOnLoadMoreListener(this::loadMore);
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setAutoLoadMore(true);
        adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(true);

        binding.competitionRecycler.setAdapter(adapter);
    }

    private void loadMore() {
        Log.d(TAG, "loadMore: ");
        new Thread(() -> {
            Log.d(TAG, "run: thread stated.");
            List<com.example.competition.Database.Model.Competition> list = CompetitionDao.getCompetition(null, null, null, 0, 1);

            mainHandler.post(() -> {
                adapter.getLoadMoreModule().loadMoreComplete();
                List<Competition> list2 = new ArrayList<>();
                for (com.example.competition.Database.Model.Competition item : list) {
                    Competition competition = new Competition();
                    competition.setName(item.getName());
                    Log.d(TAG, "competition id: " + item.getCompetitionId());
                    competition.setId(item.getCompetitionId());
                    list2.add(competition);
                    Log.d(TAG, "competition " + item.getName() + " added.");
                }

                if (list2.size() == 0) {
                    adapter.getLoadMoreModule().loadMoreEnd();
                    return;
                }

                if (adapter.getItemCount() == 0) {
                    adapter.setList(list2);
                } else {
                    adapter.addData(list2);
                }
            });
        }).start();
    }

}