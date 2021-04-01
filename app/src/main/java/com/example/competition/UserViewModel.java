package com.example.competition;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<String> testStr;

    public MutableLiveData<String> getTestStr() {
        if (testStr == null) {
            testStr = new MutableLiveData<>();
            testStr.setValue("Hello User Fragment");
        }
        return testStr;
    }

    public void setTestStr(String str) {
        testStr.setValue(str);
    }
}
