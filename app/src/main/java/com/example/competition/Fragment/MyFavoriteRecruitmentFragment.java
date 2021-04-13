package com.example.competition.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.competition.Model.MyFavoriteRecruitment;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.MyFavoriteRecruitmentAdapter;
import com.example.competition.databinding.FragmentMyFavoriteRecruitmentBinding;

import java.util.ArrayList;
import java.util.List;

public class MyFavoriteRecruitmentFragment extends Fragment {

    private FragmentMyFavoriteRecruitmentBinding binding;
    private List<MyFavoriteRecruitment> recruitments = new ArrayList<>();

    public MyFavoriteRecruitmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyFavoriteRecruitmentBinding.inflate(inflater);

        initTestData();
        initRecycler();

        return binding.getRoot();
    }

    private void initTestData() {
        for (int i = 0; i < 10; i++) {
            recruitments.add(new MyFavoriteRecruitment());
        }
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.myFavoriteRecruitmentRecycler.setLayoutManager(manager);
        MyFavoriteRecruitmentAdapter adapter = new MyFavoriteRecruitmentAdapter(R.layout.layout_my_favorite_recruitment_item, recruitments);

        binding.myFavoriteRecruitmentRecycler.setAdapter(adapter);
    }
}