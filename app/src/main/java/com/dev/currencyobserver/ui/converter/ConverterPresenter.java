package com.dev.currencyobserver.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dev.currencyobserver.ui.currencylist.CurrencyListData;
import com.dev.currencyobserver.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConverterPresenter extends Fragment {
    private ConverterView converterView;
    private double currentValue;
    private double currentNominal;
    private ConverterModel converterModel;

    private Spinner spinner;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        converterView = new ViewModelProvider(this).get(ConverterView.class);
        root = inflater.inflate(R.layout.fragment_converter, container, false);

        spinner = root.findViewById(R.id.spinner);
        Ion.with(getActivity().getApplicationContext())
                .load("https://www.cbr-xml-daily.ru/daily_json.js")
                .asJsonObject()
                .setCallback((Exception e, JsonObject result) -> {
                    converterModel = new ConverterModel(result);
                    loadSpinnerValues(converterModel.getValutesArray());
                    setConvertButtonOnclickListener();
                });

        return root;
    }

    private void loadSpinnerValues(ArrayList<String> spinnerArray) {
        //TODO это перенести во View
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                String selectedValuteName = spinner.getSelectedItem().toString();
                currentValue = converterModel.getValue(selectedValuteName);
                currentNominal = converterModel.getNominals(selectedValuteName);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setConvertButtonOnclickListener() {
        Button convertButton = root.findViewById(R.id.convertButton);
        convertButton.setOnClickListener(v -> {
            TextInputEditText convertFromInput = root.findViewById(R.id.convertFromField);
            TextInputEditText convertToInput = root.findViewById(R.id.convertToField);

            String inputText = convertFromInput.getText().toString();
            String regex = "[0-9]+";

            if (inputText.isEmpty()) {
                Toast.makeText(getActivity().getApplicationContext(), "Введите значение", Toast.LENGTH_SHORT).show();
            } else if (!inputText.matches(regex)) {
                Toast.makeText(getActivity().getApplicationContext(), "Введите числовое значение", Toast.LENGTH_SHORT).show();
            } else {
                double inputValue = Double.parseDouble(inputText);
                double outputValue = (inputValue * currentValue) / currentNominal;
                String outputText = String.format("%.2f", outputValue) + "₽";
                convertToInput.setText(outputText);
            }
        });
    }
}