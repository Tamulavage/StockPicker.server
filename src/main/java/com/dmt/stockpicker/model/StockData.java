package com.dmt.stockpicker.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StockData {

    private BigDecimal openAmount;
    private BigDecimal closeAmount;
    private BigDecimal highAmount;
    private BigDecimal lowAmount;
    private BigInteger volume;

    public StockData() {};

    public StockData(BigDecimal openAmount,
        BigDecimal closeAmount,
        BigDecimal highAmount,
        BigDecimal lowAmount,
        BigInteger volume ){
            this.openAmount = openAmount;
            this.closeAmount = closeAmount;
            this.highAmount = highAmount;
            this.lowAmount = lowAmount;
            this.volume = volume;
    }

    public BigDecimal getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(BigDecimal openAmount) {
        this.openAmount = openAmount;
    }

    public BigDecimal getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(BigDecimal closeAmount) {
        this.closeAmount = closeAmount;
    }

    public BigDecimal getHighAmount() {
        return highAmount;
    }

    public void setHighAmount(BigDecimal highAmount) {
        this.highAmount = highAmount;
    }

    public BigDecimal getLowAmount() {
        return lowAmount;
    }

    public void setLowAmount(BigDecimal lowAmount) {
        this.lowAmount = lowAmount;
    }

    public BigInteger getVolume() {
        return volume;
    }

    public void setVolume(BigInteger volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "DailyStockData [openAmount=" + openAmount + ", closeAmount=" + closeAmount + ", highAmount=" + highAmount 
                + ", lowAmount=" + lowAmount + ", volume=" + volume + "]";
    }
}
