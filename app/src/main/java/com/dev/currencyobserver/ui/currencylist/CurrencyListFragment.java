package com.dev.currencyobserver.ui.currencylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dev.currencyobserver.CurrencyList;
import com.dev.currencyobserver.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CurrencyListFragment extends Fragment {
    private CurrencyListViewModel currencyListViewModel;
    private HashMap<String, CurrencyList.Valute> valutes;
    private CurrencyList currencyList;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        currencyListViewModel = new ViewModelProvider(this).get(CurrencyListViewModel.class);
        root = inflater.inflate(R.layout.fragment_currency_list, container, false);

        Ion.with(getActivity().getApplicationContext())
                .load("https://www.cbr-xml-daily.ru/daily_json.js")
                .asJsonObject()
                .setCallback((e, result) -> {
                    processJson(result);
                });
        return root;
    }

    private void processJson(JsonObject result) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<HashMap<String, CurrencyList.Valute>>(){}.getType();
        valutes = gson.fromJson(result.get("Valute"), collectionType);
        currencyList = new CurrencyList(result.get("Date").toString(), valutes);
        fillTable(currencyList);
    }

    private void fillTable(CurrencyList currencyList) {
        TableLayout tableLayout = root.findViewById(R.id.currencyTable);
        for (Map.Entry entry : currencyList.getValutes().entrySet()) {
            CurrencyList.Valute currentValute = (CurrencyList.Valute)entry.getValue();
            String nominal = String.valueOf(currentValute.getNominal());
            String name = currentValute.getName();
            String value = String.valueOf(currentValute.getValue());

            TableRow tableRow = new TableRow(getActivity().getApplicationContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            TextView nominalValute = new TextView(getActivity().getApplicationContext());
            nominalValute.setText(nominal);
            tableRow.addView(nominalValute);

            TextView nameValute = new TextView(getActivity().getApplicationContext());
            nameValute.setText(name);
            tableRow.addView(nameValute);

            TextView valueValute = new TextView(getActivity().getApplicationContext());
            valueValute.setText(value);
            tableRow.addView(valueValute);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}