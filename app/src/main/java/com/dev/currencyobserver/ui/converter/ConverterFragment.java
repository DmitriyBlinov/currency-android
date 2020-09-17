package com.dev.currencyobserver.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dev.currencyobserver.R;
import com.google.android.material.textfield.TextInputLayout;

public class ConverterFragment extends Fragment {

    private ConverterViewModel converterViewModel;

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
        return root;
    }
}