package com.example.competition.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.competition.Activity.CompetitionDetailActivity;
import com.example.competition.Model.Competition;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.CompetitionAdapter;
import com.example.competition.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private List<Competition> competitions = new ArrayList<>();

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
        initListData();
        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.competitionRecycler.setLayoutManager(layoutManager);
        CompetitionAdapter adapter = new CompetitionAdapter(R.layout.layout_competition_item, competitions);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        adapter.setAnimationFirstOnly(false);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), CompetitionDetailActivity.class);
                startActivity(intent);
            }
        });

        binding.competitionRecycler.setAdapter(adapter);
    }

    private void initListData() {
        for (int i = 0; i < 5; i++)
            competitions.add(new Competition());
    }

}