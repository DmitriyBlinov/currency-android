package com.dev.currencyobserver.ui.converter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.currencyobserver.ui.currencylist.CurrencyListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConverterView extends ViewModel {
    MutableLiveData<String> data;

    public ConverterView() {

    }

    public LiveData<String> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadData();
        }
        return data;
    }

    private void loadData() {
        dataRepository.loadData(new Callback<String>() {
            @Override
            public void onLoad(String s) {
                data.postValue(s);
            }
        });
    }

    private void processJson() {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<HashMap<String, CurrencyListData.Valute>>(){}.getType();
        HashMap<String, CurrencyListData.Valute> valutes = gson.fromJson(jsonObject.get("Valute"), collectionType);
        CurrencyListData currencyListData = new CurrencyListData(jsonObject.get("Date").toString(), valutes);

        valutesArray = new ArrayList<>();
        values = new HashMap<>();
        nominals = new HashMap<>();

        for (Map.Entry entry : currencyListData.getValutes().entrySet()) {
            CurrencyListData.Valute currentValute = (CurrencyListData.Valute) entry.getValue();
            String name = currentValute.getName();
            valutesArray.add(name);
            values.put(name, currentValute.getValue());
            nominals.put(name, currentValute.getNominal());
        }
    }
}