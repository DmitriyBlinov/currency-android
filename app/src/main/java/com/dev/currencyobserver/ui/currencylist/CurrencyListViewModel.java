package com.dev.currencyobserver.ui.currencylist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrencyListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CurrencyListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is currency list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}