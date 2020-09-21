package com.dev.currencyobserver;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class CurrencyList {
    @SerializedName("Date")
    private String date;
    @SerializedName("PreviousDate")
    private String prevDate;
    @SerializedName("PreviousURL")
    private String prevURL;
    @SerializedName("Timestamp")
    private String timestamp;
    @SerializedName("Valute")
    public HashMap<String, Valute> valutes;

    public CurrencyList(String date, HashMap<String, Valute> valutes){
        this.date = date;
        this.valutes = valutes;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, Valute> getValutes() {
        return valutes;
    }

    public void setValutes(HashMap<String, Valute> valutes) {
        this.valutes = valutes;
    }

    public class Valute {
        @SerializedName("ID")
        private String id;
        @SerializedName("NumCode")
        private String numCode;
        @SerializedName("CharCode")
        private String charCode;
        @SerializedName("Nominal")
        private int nominal;
        @SerializedName("Name")
        private String name;
        @SerializedName("Value")
        private double value;
        @SerializedName("PreviousValue")
        private double prevValue;

        public Valute(String id, String name, double value){
            this.id = id;
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "[ nominal: " + nominal + ", name: " + name + ", value: " + value + " ]";
        }

        public void setNominal(int nominal) {
            this.nominal = nominal;
        }

        public int getNominal() {
            return nominal;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        public String getCharCode(){
            return charCode;
        }
    }
}