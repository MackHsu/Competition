package com.example.competition.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.competition.Activity.SignUpActivity;
import com.example.competition.ViewModel.UserViewModel;
import com.example.competition.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    private String TAG = "UserFragment";

    UserViewModel vm;
    FragmentUserBinding binding;

    public UserFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater);
        vm = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        initActions();
        return binding.getRoot();
    }

    private void initActions() {
        binding.DiscussLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: DiscussLayout");
            }
        });

        binding.FavoriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: FavoriteLayout");
            }
        });

        binding.UserInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}