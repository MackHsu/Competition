package com.example.competition.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.competition.Fragment.HomeFragment;
import com.example.competition.Fragment.ConversationFragment;
import com.example.competition.Fragment.UserFragment;
import com.example.competition.R;
import com.example.competition.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;
    private ActivityMainBinding binding;
    private List<Fragment> fragments = new ArrayList<>();

    private static int HOME_FRAGMENT = 0;
    private static int CONVERSATION_FRAGMENT = 1;
    private static int USER_FRAGMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // add tab listener
        binding.myTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(HOME_FRAGMENT);
                        break;
                    case 1:
                        replaceFragment(CONVERSATION_FRAGMENT);
                        break;
                    case 2:
                        replaceFragment(USER_FRAGMENT);
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fragments.add(new HomeFragment());
        fragments.add(new ConversationFragment());
        fragments.add(new UserFragment());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment.isAdded()) continue;
            transaction.add(R.id.fragment_container, fragment);
            transaction.hide(fragment);
        }
        transaction.commit();

        replaceFragment(HOME_FRAGMENT);
    }

    private void replaceFragment(int index) {
        Fragment fragment = fragments.get(index);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(currentFragment != null)
            transaction.hide(currentFragment);
        transaction.show(fragment).commit();

        currentFragment = fragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserFragment uf = (UserFragment) fragments.get(USER_FRAGMENT);
        uf.checkSignedIn();
    }
}