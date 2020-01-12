package com.dmt.stockpicker.configuration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import com.dmt.stockpicker.model.IndicatorData;
import com.dmt.stockpicker.model.StockIndicator;

// TODO: Code this
// The MACD smooth line (also known as signal line) is the 9 day exponential moving average (EMA) of the MACD

public abstract class Formulas {

    public static BigDecimal calculateSimpleMovingAverage(Integer timeFrameInDays, List<StockIndicator> stockIndicatorData){
        //SUM of closing for timeFrameInDays days dividedBy (timeFrameInDays days )
        BigDecimal sumOfClosing = new BigDecimal("0");
        for(int i=0; i<timeFrameInDays && i<stockIndicatorData.size(); i++){
            sumOfClosing = sumOfClosing.add(stockIndicatorData.get(i).getCloseAmount());
        }
        return sumOfClosing.divide(BigDecimal.valueOf(timeFrameInDays), RoundingMode.DOWN);
    }

    public static List<StockIndicator> populateExponentialMovingAverage(Integer timeFrameInDays, 
                                                                        List<StockIndicator> stockIndicatorData,
                                                                        Boolean EMAshort){
        // {close - EMA(previousDay)} * multiplier + EMA(PreviousDay)
        // NOTE: for initial starting point, use SMA versus EMA(PreviousDay)
        // TODO: need to change to update all records, not just the count in days
        for(int i=timeFrameInDays-1; i>=0 ;i--){
            StockIndicator stockIndicator = stockIndicatorData.get(i);
            IndicatorData indicatorData = stockIndicatorData.get(i).getIndicator();
            if(indicatorData == null){
                indicatorData = new IndicatorData();
            }

            BigDecimal close =  stockIndicatorData.get(i+1).getCloseAmount();
            BigDecimal prevEma ;

            if(i==(timeFrameInDays-1)){
                List<StockIndicator> temp = stockIndicatorData.subList(timeFrameInDays, stockIndicatorData.size());
                prevEma =  calculateSimpleMovingAverage(timeFrameInDays, temp);
            }
            else{
                IndicatorData indicatorDataPrevDay = stockIndicatorData.get(i+1).getIndicator();
                if(EMAshort){
                    prevEma = indicatorDataPrevDay.getEmaShort();
                }
                else {
                    prevEma = indicatorDataPrevDay.getEmaLong();
                }
            }

            BigDecimal ema = calculateExponentialMovingAverage(timeFrameInDays, close, prevEma);

            if(EMAshort){
                indicatorData.setEmaShort(ema);
            }
            else {
                indicatorData.setEmaLong(ema);
            }
            stockIndicator.setIndicator(indicatorData);
        } 
        return stockIndicatorData;
    }

    public static List<StockIndicator> populateMACD(List<StockIndicator> stockIndicatorData){
        for(int i=0; i < stockIndicatorData.size();i++){
            StockIndicator stockIndicator = stockIndicatorData.get(i);
            IndicatorData indicatorData = stockIndicatorData.get(i).getIndicator();
            if(indicatorData == null){
                break;
            }
            indicatorData.setMACD(calculateMACD(indicatorData));
            stockIndicator.setIndicator(indicatorData);
        }
        return  stockIndicatorData;
    }

    private static BigDecimal calculateMACD(IndicatorData indicatorData){
        // shoterPeriod EMA minus longerPeriod EMA
        return indicatorData.getEmaShort().subtract(indicatorData.getEmaLong());
    }

    private static BigDecimal calculateExponentialMovingAverage( Integer timeFrameInDays, BigDecimal close, BigDecimal prevEMA){
        BigDecimal retVal = close.subtract(prevEMA);
        retVal = retVal.multiply(calculateMultiplier(timeFrameInDays));
        retVal = retVal.add(prevEMA);

        MathContext roundAt4 = new MathContext(4);
        return retVal.round(roundAt4);
    }

    private static BigDecimal calculateMultiplier(Integer timeFrameInDays){
        // 2 dividedBy ( TimePeriod + 1);
        return  BigDecimal.valueOf(2).divide(new BigDecimal(timeFrameInDays + 1), 2, RoundingMode.CEILING);
    }    
}