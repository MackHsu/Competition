package com.example.competition.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.competition.Model.Competition;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<String> testStr;
    private MutableLiveData<List<Competition>> competitions;

    public void setTestStr(MutableLiveData<String> testStr) {
        this.testStr = testStr;
    }

    public MutableLiveData<List<Competition>> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(MutableLiveData<List<Competition>> competitions) {
        this.competitions = competitions;
    }

    public UserViewModel() {
        testStr = new MutableLiveData<>();
        competitions = new MutableLiveData<>();

        testStr.setValue("MainActivityViewModel");
        competitions.setValue(new ArrayList<>());
    }

    public MutableLiveData<String> getTestStr() {
        return testStr;
    }

    public void setTestStr(String str) {
        testStr.setValue(str);
    }
}
