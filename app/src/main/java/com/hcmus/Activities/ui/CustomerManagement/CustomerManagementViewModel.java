package com.hcmus.Activities.ui.CustomerManagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomerManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Customer Management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}