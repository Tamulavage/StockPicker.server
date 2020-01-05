package com.dmt.stockpicker.configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.dmt.stockpicker.model.StockIndicator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FormulasTest {

    List<StockIndicator> stockIndicatorData = new ArrayList<StockIndicator>();

    @Test
    public void calculateSimpleMovingAverageTest(){
        // Given
        if(stockIndicatorData.size() == 0){
            populateStockIndicatorData();
        }
        BigDecimal expected = new BigDecimal("11.1");

        // When
        BigDecimal actual = Formulas.calculateSimpleMovingAverage(5, stockIndicatorData);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void populateExponentialMovingAverageShortTest(){
        // Given
        BigDecimal expected = new BigDecimal("12.62");
        if(stockIndicatorData.size() == 0){
            populateStockIndicatorData();
        }
        // When
        List<StockIndicator>  actual = Formulas.populateExponentialMovingAverage(5, stockIndicatorData, true);

        // actual.forEach((x) -> System.out.println(x));
        Assert.assertEquals(expected, actual.get(0).getIndicator().getEmaShort());
    }


    @Test
    public void populateExponentialMovingAverageLongTest(){
        // Given
        BigDecimal expected = new BigDecimal("11.13");
        if(stockIndicatorData.size() == 0){
            populateStockIndicatorData();
        }
        // When
        List<StockIndicator>  actual = Formulas.populateExponentialMovingAverage(10, stockIndicatorData, false);

        // actual.forEach((x) -> System.out.println(x));
        Assert.assertEquals(expected, actual.get(0).getIndicator().getEmaLong());
    } 

    @Test
    public void populateMACDTest(){
        // Given
        BigDecimal expected = new BigDecimal("1.49");
        if(stockIndicatorData.size() == 0){
            populateStockIndicatorData();
        }
        List<StockIndicator>  actual = Formulas.populateExponentialMovingAverage(10, stockIndicatorData, false);
        actual = Formulas.populateExponentialMovingAverage(5, stockIndicatorData, true);        

        // When
        actual = Formulas.populateMACD(stockIndicatorData);  

        // actual.forEach((x) -> System.out.println(x));
        Assert.assertEquals(expected, actual.get(0).getIndicator().getMACD());
    }       

    private void populateStockIndicatorData(){
        Integer testDataAmount = 20;
        StockIndicator[] dailyStockData = new StockIndicator[testDataAmount];
        dailyStockData[0] = new StockIndicator(
            new BigDecimal("3"),    // open
            new BigDecimal("9"),    // close
            new BigDecimal("10"),   // high
            new BigDecimal("1"),    // low
            new BigInteger("10000"));   // vol
        dailyStockData[1] = new StockIndicator(
            new BigDecimal("9"),    
            new BigDecimal("15.5"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[2] = new StockIndicator(
            new BigDecimal("15.5"),    
            new BigDecimal("17"), 
            new BigDecimal("17"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[3] = new StockIndicator(
            new BigDecimal("17"),    
            new BigDecimal("9"), 
            new BigDecimal("17"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[4] = new StockIndicator(
            new BigDecimal("9"),    
            new BigDecimal("5"), 
            new BigDecimal("9"), 
            new BigDecimal(".2"), 
            new BigInteger("152000")); 
        dailyStockData[5] = new StockIndicator(
            new BigDecimal("6"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[6] = new StockIndicator(
            new BigDecimal("7"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[7] = new StockIndicator(
            new BigDecimal("8"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[8] = new StockIndicator(
            new BigDecimal("9"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[9] = new StockIndicator(
            new BigDecimal("100"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[10] = new StockIndicator(
            new BigDecimal("11"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[11] = new StockIndicator(
            new BigDecimal("1"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200")); 
        dailyStockData[12] = new StockIndicator(
            new BigDecimal("1"),    
            new BigDecimal("9"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200"));    
        dailyStockData[13] = new StockIndicator(
            new BigDecimal("1"),    
            new BigDecimal("9.5"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200"));    
        dailyStockData[14] = new StockIndicator(
            new BigDecimal("1"),    
            new BigDecimal("10"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200"));
        dailyStockData[15] = new StockIndicator(
            new BigDecimal("1"),    
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200"));            
        dailyStockData[16] = new StockIndicator(
            new BigDecimal("1"),    
            new BigDecimal("8"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("10200"));
        dailyStockData[17] = new StockIndicator(
            new BigDecimal("1"),    
            new BigDecimal("7.5"), 
            new BigDecimal("15"), 
            new BigDecimal("15"), 
            new BigInteger("112200"));
        dailyStockData[18] = new StockIndicator(
            new BigDecimal("1111"),    
            new BigDecimal("10"), 
            new BigDecimal("11111"), 
            new BigDecimal("15"), 
            new BigInteger("2200")); 
        dailyStockData[19] = new StockIndicator(
            new BigDecimal("5.5"),    
            new BigDecimal("8.2"), 
            new BigDecimal("11111"), 
            new BigDecimal("152"), 
            new BigInteger("2202220"));             
        for(int i=0; i< testDataAmount; i++){
            this.stockIndicatorData.add( dailyStockData[i]);
        }            
    }    
 }