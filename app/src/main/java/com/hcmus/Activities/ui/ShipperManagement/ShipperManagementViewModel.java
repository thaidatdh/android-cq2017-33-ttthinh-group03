package com.hcmus.Activities.ui.ShipperManagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShipperManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShipperManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}