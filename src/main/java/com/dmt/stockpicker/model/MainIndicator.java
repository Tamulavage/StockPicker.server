package com.dmt.stockpicker.model;

import java.math.BigDecimal;

import com.dmt.stockpicker.enums.*;

public class MainIndicator {
    Suggestion suggestion ;
    BigDecimal slowEMA;
    BigDecimal fastEMA;
    BigDecimal macd;

    public MainIndicator(){
        suggestion = Suggestion.HOLD;
    }

    public Suggestion getSuggest() {
        return suggestion;
    }

    public void setSuggest(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public BigDecimal getSlowEMA() {
        return slowEMA;
    }

    public void setSlowEMA(BigDecimal slowEMA) {
        this.slowEMA = slowEMA;
    }

    public BigDecimal getFastEMA() {
        return fastEMA;
    }

    public void setFastEMA(BigDecimal fastEMA) {
        this.fastEMA = fastEMA;
    }

    public BigDecimal getMacd() {
        return macd;
    }

    public void setMacd(BigDecimal macd) {
        this.macd = macd;
    }
}