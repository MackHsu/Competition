package com.example.competition.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.competition.Fragment.HomeFragment;
import com.example.competition.Fragment.ConversationFragment;
import com.example.competition.Fragment.UserFragment;
import com.example.competition.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private Fragment currentFragment;

    private List<Fragment> fragments = new ArrayList<>();
    private static int HOME_FRAGMENT = 0;
    private static int CONVERSATION_FRAGMENT = 1;
    private static int USER_FRAGMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.my_tabs);

        // add tab listener
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
}