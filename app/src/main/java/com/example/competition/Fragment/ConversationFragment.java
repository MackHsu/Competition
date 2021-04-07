package com.example.competition.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.example.competition.Model.Conversation;
import com.example.competition.R;
import com.example.competition.RecyclerViewAdapter.ConversationAdapter;
import com.example.competition.databinding.FragmentConversationBinding;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.popupwindow.easypopup.EasyPopup;
import com.xuexiang.xui.widget.popupwindow.easypopup.HorizontalGravity;
import com.xuexiang.xui.widget.popupwindow.easypopup.VerticalGravity;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimpleExpandablePopup;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends Fragment {

    private FragmentConversationBinding binding;
    private List<Conversation> conversations = new ArrayList<>();
    private XUISimplePopup mPopup;

    public ConversationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConversationBinding.inflate(inflater);
        initTestData();
        initRecyclerView();
        initPopup();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        // init recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.notificationsRecycler.setLayoutManager(layoutManager);
        ConversationAdapter adapter = new ConversationAdapter(R.layout.layout_conversation_item, conversations);
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        adapter.setAnimationFirstOnly(false);

        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mPopup.showDown(view);
                return false;
            }
        });

        binding.notificationsRecycler.setAdapter(adapter);
    }

    private void initTestData() {
        for (int i = 0; i < 7; i++) {
            conversations.add(new Conversation());
        }
    }

    private void initPopup() {
        mPopup = new XUISimplePopup(getContext(), new AdapterItem[] {
                new AdapterItem("删除", R.drawable.ic_delete)
        })
                .create((adapter, item, position) -> {
                    Toast.makeText(getContext(), "you clicked delete.", Toast.LENGTH_SHORT).show();
        });
    }
}