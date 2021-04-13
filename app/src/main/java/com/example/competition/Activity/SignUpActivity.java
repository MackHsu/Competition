package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.competition.Fragment.SignInFragment;
import com.example.competition.Fragment.SignUpFragment;
import com.example.competition.R;
import com.example.competition.databinding.ActivitySignUpBinding;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private Fragment currentFragment;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentTransaction transaction;
    public static int SIGN_IN_FRAGMENT = 0;
    public static int SIGN_UP_FRAGMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragments.add(new SignInFragment());
        fragments.add(new SignUpFragment());

        for (Fragment fragment: fragments) {
            if (fragment.isAdded()) continue;
            transaction.add(R.id.sign_up_fragment_container, fragment);
            transaction.hide(fragment);
        }
        transaction.commit();

        binding.signUpTitlebar.setLeftClickListener(view -> {
            finish();
        });

        replaceFragment(SIGN_IN_FRAGMENT);
    }

    public void replaceFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = fragments.get(index);
        if(currentFragment != null)
            transaction.hide(currentFragment);
        transaction.show(fragment).commit();

        currentFragment = fragment;
    }

}