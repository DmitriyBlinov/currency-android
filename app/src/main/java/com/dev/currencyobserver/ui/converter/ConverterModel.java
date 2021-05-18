package com.dev.currencyobserver.ui.converter;

import com.dev.currencyobserver.ui.currencylist.CurrencyListData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConverterModel {
    private JsonObject jsonObject;
    private HashMap<String, Double> values;
    private HashMap<String, Integer> nominals;
    private ArrayList<String> valutesArray;

    public ConverterModel(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void processJson() {
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

    public ArrayList<String> getValutesArray() {
        processJson();
        return valutesArray;
    }

    public double getValue(String valuteName) {
        return values.get(valuteName);
    }

    public double getNominals(String valuteName) {
        return nominals.get(valuteName);
    }
}