package com.dev.currencyobserver.ui.converter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConverterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConverterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is converter fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}