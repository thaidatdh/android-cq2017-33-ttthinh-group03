package com.hcmus.Activities.ui.ShopInformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopInformationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShopInformationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Shop Information fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}