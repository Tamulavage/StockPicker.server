package com.dmt.stockpicker.model;

import com.dmt.stockpicker.enums.*;

public class MainIndicator {
    Suggestion suggestion ;

    public MainIndicator(){
        suggestion = Suggestion.HOLD;
    }

    public Suggestion getSuggest() {
        return suggestion;
    }

    public void setSuggest(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}