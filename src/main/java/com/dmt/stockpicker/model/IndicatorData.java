package com.dmt.stockpicker.model;
import java.math.BigDecimal;

public class IndicatorData {

    BigDecimal supportLine;
    BigDecimal resistanceLine;
    BigDecimal pivotPoint;
    Integer timeFrameInDays;
    BigDecimal emaShort;
    BigDecimal emaLong;
    BigDecimal MACD;

    public IndicatorData(){   
        setEmaShort(new BigDecimal("0"));
        setEmaLong(new BigDecimal("0"));
    }
    
    public IndicatorData(
                    BigDecimal supportLine, 
                    BigDecimal resistanceLine, 
                    Integer timeFrameInDays) {
        this.supportLine = supportLine;
        this.resistanceLine = resistanceLine;
        this.timeFrameInDays = timeFrameInDays;
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

    public BigDecimal getPivotPoint() {
        return pivotPoint;
    }

    public void setPivotPoint(BigDecimal pivotPoint) {
        this.pivotPoint = pivotPoint;
    }

    public BigDecimal getEmaShort() {
        return emaShort;
    }

    public void setEmaShort(BigDecimal emaShort) {
        this.emaShort = emaShort;
    }

    public BigDecimal getEmaLong() {
        return emaLong;
    }

    public void setEmaLong(BigDecimal emaLong) {
        this.emaLong = emaLong;
    }

    public BigDecimal getMACD() {
        return MACD;
    }

    public void setMACD(BigDecimal mACD) {
        MACD = mACD;
    }

    @Override
    public String toString() {
        return "IndicatorData [MACD=" + MACD + ", emaLong=" + emaLong + ", emaShort=" + emaShort + ", pivotPoint="
                + pivotPoint + ", resistanceLine=" + resistanceLine + ", supportLine="
                + supportLine + ", timeFrameInDays=" + timeFrameInDays + "]";
    }

}
