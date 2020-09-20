package com.dev.currencyobserver.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dev.currencyobserver.CurrencyList;
import com.dev.currencyobserver.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConverterFragment extends Fragment {

    private ConverterViewModel converterViewModel;
    private double currentValue;
    private double currentNominal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        converterViewModel =
                ViewModelProviders.of(this).get(ConverterViewModel.class);
        View root = inflater.inflate(R.layout.fragment_converter, container, false);
        final TextInputLayout textInputLayout = root.findViewById(R.id.convertFrom);
        converterViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textInputLayout.setText(s);
            }
        });
        LinearLayout layout = new LinearLayout(getActivity().getApplicationContext());

        Ion.with(getActivity().getApplicationContext())
                .load("https://www.cbr-xml-daily.ru/daily_json.js")
                .asJsonObject()
                .setCallback((e, result) -> {
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<HashMap<String, CurrencyList.Valute>>(){}.getType();
                    HashMap<String, CurrencyList.Valute> valutes = gson.fromJson(result.get("Valute"), collectionType);
                    CurrencyList currencyList = new CurrencyList(result.get("Date").toString(), valutes);
                    Spinner spinner = getView().findViewById(R.id.spinner);
                    ArrayList<String> spinnerArray = new ArrayList<>();
                    HashMap<String, Double> values = new HashMap<>();
                    HashMap<String, Integer> nominals = new HashMap<>();

                    for (Map.Entry entry : currencyList.getValutes().entrySet()) {
                        CurrencyList.Valute currentValute = (CurrencyList.Valute) entry.getValue();
                        String name = currentValute.getName();
                        spinnerArray.add(name);
                        values.put(name, currentValute.getValue());
                        nominals.put(name, currentValute.getNominal());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
                    spinner.setAdapter(spinnerArrayAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent,
                                                   View itemSelected, int selectedItemPosition, long selectedId) {

                            String selectedItem = spinner.getSelectedItem().toString();
                            currentValue = values.get(selectedItem);
                            currentNominal = nominals.get(selectedItem);
                        }
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    Button convertButton = getView().findViewById(R.id.convertButton);
                    convertButton.setOnClickListener(v -> {
                        TextInputEditText convertFromInput = getView().findViewById(R.id.convertFromField);
                        TextInputEditText convertToInput = getView().findViewById(R.id.convertToField);

                        String inputText = convertFromInput.getText().toString();
                        String regex = "[0-9]+";

                        if (inputText.isEmpty()) {
                            Toast.makeText(getActivity().getApplicationContext(), "Введите значение", Toast.LENGTH_SHORT).show();
                        } else if (!inputText.matches(regex)) {
                            Toast.makeText(getActivity().getApplicationContext(), "Введите числовое значение", Toast.LENGTH_SHORT).show();
                        } else {
                            double inputValue = Double.parseDouble(inputText);
                            double outputValue = (inputValue * currentValue) / currentNominal;
                            String outputText = String.format("%.2f", outputValue);

                            convertToInput.setText(outputText);
                        }

                    });
                });

        return root;
    }
}