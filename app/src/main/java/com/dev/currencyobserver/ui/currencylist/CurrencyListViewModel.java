package com.dev.currencyobserver.ui.currencylist;

import android.widget.TableLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrencyListViewModel extends ViewModel {
    private MutableLiveData<TableLayout> mTableLayout;

    public CurrencyListViewModel() {
        mTableLayout = new MutableLiveData<>();
    }

    public LiveData<TableLayout> getTable() {
        return mTableLayout;
    }
}