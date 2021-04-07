package com.example.competition.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<String> testStr;

    public UserViewModel() {
        testStr = new MutableLiveData<>();
        testStr.setValue("MainActivityViewModel");
    }

    public MutableLiveData<String> getTestStr() {
        return testStr;
    }

    public void setTestStr(String str) {
        testStr.setValue(str);
    }
}
