package com.dmt.stockpicker.services;

import com.dmt.stockpicker.model.StockSymbol;
import com.dmt.stockpicker.model.StockIndicator;
import com.dmt.stockpicker.model.WatchedStock;
import com.dmt.stockpicker.repository.StockSymbolRepository;
import com.dmt.stockpicker.repository.WatchedStockRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WatchedStockServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WatchedStockRepository watchedStockRepository;

    @Mock
    private StockSymbolRepository symbolRepository;

    @InjectMocks
    private WatchedStockService watchedStockService;

    @Ignore
    @Before
    public void setup(){
    }

    @Test
    public void testAddWatchStock(){
        String symbol = "test";
        StockSymbol stockSymbol = new StockSymbol(1, symbol);
        WatchedStock watchedStock = new WatchedStock( null, null, stockSymbol );
        WatchedStock watchedStockExpected = new WatchedStock(null, null, stockSymbol );

        when(symbolRepository.findBySymbol(symbol)).thenReturn(stockSymbol);
        when(watchedStockRepository.save(watchedStock)).thenReturn(watchedStockExpected);

        WatchedStock actual = watchedStockService.watchNewStock(watchedStock);

        Assert.assertEquals(watchedStockExpected,actual );
    }

    @Test
    public void testEndWatchStock(){
        String symbol = "test";
        StockSymbol stockSymbol = new StockSymbol(1, symbol);
        WatchedStock watchedStock = new WatchedStock( null, null, stockSymbol );
        WatchedStock watchedStockExpected = new WatchedStock(null, LocalDate.now(), stockSymbol );

        when(watchedStockRepository.getOne(watchedStock.getId())).thenReturn(watchedStock);
        when(watchedStockRepository.save(watchedStock)).thenReturn(watchedStockExpected);

        WatchedStock actual = watchedStockService.stopWatchingStock(watchedStock.getId());

        Assert.assertEquals(watchedStockExpected,actual );
    }

    @Test
    public void populateDailyIndicatorDataTest() {
        // given        
        ArrayList<StockIndicator> dailyStock = new ArrayList<StockIndicator>();
        StockIndicator[] dailyStockData = new StockIndicator[3];
        dailyStockData[0] = new StockIndicator(
                new BigDecimal("3"),    // open
                new BigDecimal("9"),    // close
                new BigDecimal("10"),   // high
                new BigDecimal("1"),    // low
                new BigInteger("10000"));   // vol
        dailyStockData[1] = new StockIndicator(
                new BigDecimal("1"),    
                new BigDecimal("9"), 
                new BigDecimal("15"), 
                new BigDecimal("15"), 
                new BigInteger("10200"));    
        dailyStockData[2] = new StockIndicator(
                new BigDecimal("15"),
                new BigDecimal("11"), 
                new BigDecimal("15"), 
                new BigDecimal("1"), 
                new BigInteger("20000"));   

        for(int i=0; i< 3; i++){
            dailyStock.add( dailyStockData[i]);
        }

        BigDecimal expectedResitance = new BigDecimal("27");
        BigDecimal expectedSupport = new BigDecimal("13");    

        // When
        List<StockIndicator> actual = watchedStockService.populateIndicatorsData(dailyStock);

        // then
        Assert.assertEquals(expectedResitance, actual.get(1).getIndicator().getResistanceLine());
        Assert.assertEquals(expectedSupport, actual.get(1).getIndicator().getSupportLine());
    }

     @Test
    public void testGetRecentStockValuesMockedEndPoint() {
        // Given
        String stockSymbol = "abc";
        BigDecimal expectedClose  =  new BigDecimal(101.0000);
        BigDecimal expectedOpen  =  new BigDecimal(100.0000);
        BigDecimal expectedLow  =  new BigDecimal(75.0000);
        BigDecimal expectedHigh  =  new BigDecimal(200.0000);
        BigInteger expectedVol  =  new BigInteger("123112");

        String mockedReturn = "{\"Time Series (Daily)\": { "
                                + "\"2019-10-04\": {"
                                + "\"1. open\": \"" + expectedOpen + "\","
                                + "\"2. high\": \"" + expectedHigh + "\","
                                + "\"3. low\": \"" + expectedLow + "\","
                                + "\"4. close\": \"" + expectedClose + "\","
                                + "\"5. volume\": \"" + expectedVol + "\""
                                + "}}}";
        ResponseEntity<String> response = new ResponseEntity<String>(mockedReturn , HttpStatus.ACCEPTED);

        Mockito.when(restTemplate.getForEntity(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(Class.class)
                ))
             .thenReturn(response);
        
        // When
        ArrayList<StockIndicator> watchedStockActual = watchedStockService.getRecentStockValues(stockSymbol);
               
        // Then
        Assert.assertEquals(0, watchedStockActual.get(0).getOpenAmount().compareTo(expectedOpen)); 
        Assert.assertEquals(0, watchedStockActual.get(0).getCloseAmount().compareTo(expectedClose));
        Assert.assertEquals(0, watchedStockActual.get(0).getLowAmount().compareTo(expectedLow));
        Assert.assertEquals(0, watchedStockActual.get(0).getHighAmount().compareTo(expectedHigh));                
        Assert.assertEquals(0, watchedStockActual.get(0).getVolume().compareTo(expectedVol));  

    }

}