package com.dev.currencyobserver.ui.currencylist;

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

public class CurrencyListFragment extends Fragment {

    private CurrencyListViewModel currencyListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        currencyListViewModel =
                ViewModelProviders.of(this).get(CurrencyListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_currency_list, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        currencyListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}