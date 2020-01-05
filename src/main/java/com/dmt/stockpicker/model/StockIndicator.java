package com.dmt.stockpicker.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StockIndicator extends StockData {
    private IndicatorData indicatorData;
    private LocalDateTime localDateTime;
    private LocalDate localDate;
    
    public StockIndicator(BigDecimal openAmount,
        BigDecimal closeAmount,
        BigDecimal highAmount,
        BigDecimal lowAmount,
        BigInteger volume ){
            super(openAmount, closeAmount,highAmount ,lowAmount, volume);
    }

    public StockIndicator(BigDecimal openAmount,
        BigDecimal closeAmount,
        BigDecimal highAmount,
        BigDecimal lowAmount,
        BigInteger volume,
        LocalDate localDate ){
            super(openAmount, closeAmount, highAmount ,lowAmount, volume);
            this.localDate = localDate;
    }

    public StockIndicator(){
    }

    public IndicatorData getIndicator() {
        return indicatorData;
    }

    public void setIndicator(IndicatorData indicatorData) {
        this.indicatorData = indicatorData;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "StockIndicator [" + indicatorData + ", localDate=" + localDate + ", localDateTime="
                + localDateTime + "] " + super.toString();
    }

    
}