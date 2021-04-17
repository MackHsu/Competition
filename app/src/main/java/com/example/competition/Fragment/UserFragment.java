package com.example.competition.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.competition.Activity.MyDiscussActivity;
import com.example.competition.Activity.MyFavoriteActivity;
import com.example.competition.Activity.MyRecruitmentActivity;
import com.example.competition.Activity.SignUpActivity;
import com.example.competition.Model.MyRecruitment;
import com.example.competition.MyApp;
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
        checkSignedIn();
        return binding.getRoot();
    }

    private void initActions() {
        binding.DiscussLayout.setOnClickListener(view -> {
            Intent intent = new Intent(MyApp.getContext(), MyDiscussActivity.class);
            startActivity(intent);
        });

        binding.FavoriteLayout.setOnClickListener(view -> {
            Intent intent = new Intent(MyApp.getContext(), MyFavoriteActivity.class);
            startActivity(intent);
        });

        binding.TeamLayout.setOnClickListener(view -> {
            Intent intent = new Intent(MyApp.getContext(), MyRecruitmentActivity.class);
            startActivity(intent);
        });

        binding.UserInfoLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SignUpActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    checkSignedIn();
                }
                break;
            default:
        }

    }

    public void checkSignedIn() {
        SharedPreferences sp = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", "not signed in");
        Log.d(TAG, "checkSignedIn: userId: " + userId);
    }
}