package com.zipcode.stockpicker.model;

// TODO: Deduplicate this with StockHistory class once determined which one to use

public class History {
    private Double openAmount;
    private Double closeAmount;
    private Double highAmount;
    private Double lowAmount;
    private Integer volume;

    public Double getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(Double openAmount) {
        this.openAmount = openAmount;
    }

    public Double getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(Double closeAmount) {
        this.closeAmount = closeAmount;
    }

    public Double getHighAmount() {
        return highAmount;
    }

    public void setHighAmount(Double highAmount) {
        this.highAmount = highAmount;
    }

    public Double getLowAmount() {
        return lowAmount;
    }

    public void setLowAmount(Double lowAmount) {
        this.lowAmount = lowAmount;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
}