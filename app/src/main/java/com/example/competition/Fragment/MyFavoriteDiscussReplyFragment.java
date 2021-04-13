package com.example.competition.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.competition.Model.MyFavoriteDiscussReply;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.MyFavoriteDiscussReplyAdapter;
import com.example.competition.databinding.FragmentMyFravoriteDiscussReplyBinding;

import java.util.ArrayList;
import java.util.List;

public class MyFavoriteDiscussReplyFragment extends Fragment {

    private FragmentMyFravoriteDiscussReplyBinding binding;
    private List<MyFavoriteDiscussReply> replies = new ArrayList<>();

    public MyFavoriteDiscussReplyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyFravoriteDiscussReplyBinding.inflate(inflater);

        initTestData();
        initRecycler();

        return binding.getRoot();
    }

    private void initTestData() {
        for (int i = 0; i < 10; i++) {
            replies.add(new MyFavoriteDiscussReply());
        }
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.myFavoriteDiscussReplyRecycler.setLayoutManager(manager);
        MyFavoriteDiscussReplyAdapter adapter = new MyFavoriteDiscussReplyAdapter(R.layout.layout_my_favorite_discuss_reply_item, replies);

        binding.myFavoriteDiscussReplyRecycler.setAdapter(adapter);
    }
}