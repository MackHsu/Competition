package com.example.competition.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.competition.Activity.SignUpActivity;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.databinding.FragmentSignUpBinding;
import com.xuexiang.xui.utils.DensityUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater);

        binding.signUpToSignIn.setOnClickListener((view) -> {
            SignUpActivity activity = (SignUpActivity) getActivity();
            activity.replaceFragment(SignUpActivity.SIGN_IN_FRAGMENT);
        });

        // set radius and shadow
        int radius = DensityUtils.dp2px(MyApp.getContext(), 15);
        int elevation = DensityUtils.dp2px(MyApp.getContext(), 10);
        binding.signUpXuiLayout.setRadiusAndShadow(radius, elevation, 0.25F);

        return binding.getRoot();
    }
}