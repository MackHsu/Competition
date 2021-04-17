package com.example.competition.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.competition.Activity.SignUpActivity;
import com.example.competition.Database.Dao.UserDao;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.databinding.FragmentSignUpBinding;
import com.xuexiang.xui.utils.DensityUtils;

public class SignUpFragment extends Fragment {

    private String TAG = "SignUpFragment";
    private FragmentSignUpBinding binding;
    private Handler mainHandler;

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
        mainHandler = new Handler(Looper.getMainLooper());

        binding.signUpToSignIn.setOnClickListener((view) -> {
            SignUpActivity activity = (SignUpActivity) getActivity();
            activity.replaceFragment(SignUpActivity.SIGN_IN_FRAGMENT);
        });

        // set radius and shadow
        int radius = DensityUtils.dp2px(MyApp.getContext(), 15);
        int elevation = DensityUtils.dp2px(MyApp.getContext(), 10);
        binding.signUpXuiLayout.setRadiusAndShadow(radius, elevation, 0.25F);

        binding.signUpBtn.setOnClickListener(view -> {
            if (!inputCheck()) return;
            new Thread(() -> {
                int returnCode = UserDao.signUp(binding.signUpAccount.getEditValue(), binding.signUpPassword.getEditValue());
                mainHandler.post(() -> {
                    if (returnCode == UserDao.SIGN_UP_OK) {
                            Toast.makeText(MyApp.getContext(), "注册成功，请登录", Toast.LENGTH_SHORT).show();
                            SignUpActivity activity = (SignUpActivity) getActivity();
                            activity.replaceFragment(SignUpActivity.SIGN_IN_FRAGMENT);
                    } else if (returnCode == UserDao.SIGN_UP_FAIL) {
                        Toast.makeText(MyApp.getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        return binding.getRoot();
    }

    private boolean inputCheck() {
        String account = binding.signUpAccount.getEditValue();
        String password = binding.signUpPassword.getEditValue();
        String password2 = binding.signUpPassword2.getEditValue();

        if (account == null || account.length() == 0) {
            binding.signUpAccount.setError("帐号不能为空");
            return false;
        }

        if (password == null || password.length() == 0) {
            binding.signUpPassword.setError("密码不能为空");
            return false;
        }

        if (password2 == null || password2.length() == 0 || !password2.equals(password)) {
            binding.signUpPassword2.setError("两次输入的帐号密码不一致");
            return false;
        }
        return true;
    }
}