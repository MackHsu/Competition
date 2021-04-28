package com.example.competition.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.competition.Activity.SignUpActivity;
import com.example.competition.Database.Dao.UserDao;
import com.example.competition.MyApp;
import com.example.competition.R;
import com.example.competition.databinding.FragmentSignInBinding;
import com.xuexiang.xui.utils.DensityUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private Handler mainHandler;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater);
        mainHandler = new Handler(Looper.getMainLooper());

        binding.signInToSignUp.setOnClickListener(view -> {
            SignUpActivity activity = (SignUpActivity) getActivity();
            if (activity != null) activity.replaceFragment(SignUpActivity.SIGN_UP_FRAGMENT);
        });
        
        binding.signInBtn.setOnClickListener((view) -> {
            if (!inputCheck()) return;
            new Thread(() -> {
                String userId = UserDao.signIn(binding.signInAccount.getEditValue(), binding.signInPassword.getEditValue());
                mainHandler.post(() -> {
                    if (userId == null)
                        Toast.makeText(MyApp.getContext(), "登录失败，用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(MyApp.getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = MyApp.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                        editor.putString("userId", userId);
                        editor.commit();
                        finishWithResult(Activity.RESULT_OK);
                    }
                });
            }).start();
        });

        // set radius and shadow
        int radius = DensityUtils.dp2px(MyApp.getContext(), 15);
        int elevation = DensityUtils.dp2px(MyApp.getContext(), 10);
        binding.signInXuiLayout.setRadiusAndShadow(radius, elevation, 0.25F);

        return binding.getRoot();
    }
    
    private boolean inputCheck() {
        String account = binding.signInAccount.getEditValue();
        String password = binding.signInPassword.getEditValue();
        
        if (account == null || account.length() == 0) {
            binding.signInAccount.setError("帐号不能为空");
            return false;
        }
        if (password == null || password.length() == 0) {
            binding.signInPassword.setError("密码不能为空");
            return false;
        }
        return true;
    }

    private void finishWithResult(int result) {
        Intent intent = new Intent();
        getActivity().setResult(result, intent);
        getActivity().finish();
    }
}