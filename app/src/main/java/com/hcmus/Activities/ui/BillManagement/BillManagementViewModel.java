package com.hcmus.Activities.ui.BillManagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BillManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BillManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Bill Management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}