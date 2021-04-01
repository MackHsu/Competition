package com.example.competition;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.competition.databinding.FragmentUserBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

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
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user, container, false);
        binding = FragmentUserBinding.inflate(inflater);
        vm = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        vm.getTestStr().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.userText.setText(s);
            }
        });
        return binding.getRoot();
    }
}