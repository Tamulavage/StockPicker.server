package com.dmt.stockpicker.services;

import com.dmt.stockpicker.model.DailyStockData;
import com.dmt.stockpicker.model.StockSymbol;
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
import java.util.TreeMap;
import java.util.Date;

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
        TreeMap<Date, DailyStockData> watchedStockActual = watchedStockService.getRecentStockValues(stockSymbol);

        System.out.println( watchedStockActual.firstEntry().getValue().getOpenAmount());
        
        // Then
        Assert.assertEquals(0, watchedStockActual.firstEntry().getValue().getOpenAmount().compareTo(expectedOpen)); 
        Assert.assertEquals(0, watchedStockActual.firstEntry().getValue().getCloseAmount().compareTo(expectedClose));
        Assert.assertEquals(0, watchedStockActual.firstEntry().getValue().getLowAmount().compareTo(expectedLow));
        Assert.assertEquals(0, watchedStockActual.firstEntry().getValue().getHighAmount().compareTo(expectedHigh));                
        Assert.assertEquals(0, watchedStockActual.firstEntry().getValue().getVolume().compareTo(expectedVol));        

    }

}