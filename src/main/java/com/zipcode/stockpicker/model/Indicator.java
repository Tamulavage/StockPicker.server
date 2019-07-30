package com.zipcode.stockpicker.model;
import java.math.BigDecimal;

public class Indicator {

    StockSymbol stock;
    BigDecimal supportLine;
    BigDecimal resistanceLine;
    Integer timeFrameInDays;
    String suggestedAction;
    Integer indicatorStrength;

    public Indicator(){   
    }

    
    public Indicator(StockSymbol stock, BigDecimal supportLine, BigDecimal resistanceLine, Integer timeFrameInDays) {
        this.stock = stock;
        this.supportLine = supportLine;
        this.resistanceLine = resistanceLine;
        this.timeFrameInDays = timeFrameInDays;
    }

    public StockSymbol getStock() {
        return stock;
    }

    public void setStock(StockSymbol stock) {
        this.stock = stock;
    }

    public BigDecimal getSupportLine() {
        return supportLine;
    }

    public void setSupportLine(BigDecimal supportLine) {
        this.supportLine = supportLine;
    }

    public BigDecimal getResistanceLine() {
        return resistanceLine;
    }

    public void setResistanceLine(BigDecimal resistanceLine) {
        this.resistanceLine = resistanceLine;
    }

    public Integer getTimeFrameInDays() {
        return timeFrameInDays;
    }

    public void setTimeFrameInDays(Integer timeFrameInDays) {
        this.timeFrameInDays = timeFrameInDays;
    }

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public void setSuggestedAction(String suggestedAction) {
        this.suggestedAction = suggestedAction;
    }

    public Integer getIndicatorStrength() {
        return indicatorStrength;
    }

    public void setIndicatorStrength(Integer indicatorStrength) {
        this.indicatorStrength = indicatorStrength;
    }

}
