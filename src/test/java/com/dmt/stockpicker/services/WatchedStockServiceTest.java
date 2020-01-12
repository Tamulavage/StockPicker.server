package com.dmt.stockpicker.services;

import com.dmt.stockpicker.model.StockSymbol;
import com.dmt.stockpicker.enums.Suggestion;
import com.dmt.stockpicker.model.MainIndicator;
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
import org.mockito.BDDMockito;
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
import java.util.Optional;

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

    ArrayList<StockIndicator> dailyStock = new ArrayList<StockIndicator>();

    @Ignore
    @Before
    public void setup() {
    }

    @Test
    public void testAddWatchStock() {
        String symbol = "test";
        StockSymbol stockSymbol = new StockSymbol(1, symbol);
        WatchedStock watchedStock = new WatchedStock(null, null, stockSymbol);
        WatchedStock watchedStockExpected = new WatchedStock(null, null, stockSymbol);

        when(symbolRepository.findBySymbol(symbol)).thenReturn(stockSymbol);
        when(watchedStockRepository.save(watchedStock)).thenReturn(watchedStockExpected);

        WatchedStock actual = watchedStockService.watchNewStock(watchedStock);

        Assert.assertEquals(watchedStockExpected, actual);
    }

    @Test
    public void testEndWatchStock() {
        String symbol = "test";
        StockSymbol stockSymbol = new StockSymbol(1, symbol);
        WatchedStock watchedStock = new WatchedStock(null, null, stockSymbol);
        WatchedStock watchedStockExpected = new WatchedStock(null, LocalDate.now(), stockSymbol);

        when(watchedStockRepository.getOne(watchedStock.getId())).thenReturn(watchedStock);
        when(watchedStockRepository.save(watchedStock)).thenReturn(watchedStockExpected);

        WatchedStock actual = watchedStockService.stopWatchingStock(watchedStock.getId());

        Assert.assertEquals(watchedStockExpected, actual);
    }

    @Test
    public void populateDailyIndicatorDataTest() {
        // given
        if (dailyStock.size() == 0) {
            populateStockIndicatorData();
        }

        BigDecimal expectedResitance = new BigDecimal("27");
        BigDecimal expectedSupport = new BigDecimal("13");

        // When
        List<StockIndicator> actual = watchedStockService.populateTrendLines(dailyStock);

        // then
        Assert.assertEquals(expectedResitance, actual.get(1).getIndicator().getResistanceLine());
        Assert.assertEquals(expectedSupport, actual.get(1).getIndicator().getSupportLine());
    }

    @Test
    public void populateRawIndicatorDataTest() {
        // given
        if (dailyStock.size() == 0) {
            populateStockIndicatorData();
        }
        BigDecimal expectedMACD = new BigDecimal("0.093");
        BigDecimal expectedEMALong = new BigDecimal("8.964");

        // When
        List<StockIndicator> actual = watchedStockService.populateRawIndicatorData(dailyStock);

        // Then
        Assert.assertEquals(expectedMACD, actual.get(0).getIndicator().getMACD());
        Assert.assertEquals(expectedEMALong, actual.get(0).getIndicator().getEmaLong());
    }

    
    @Test
    public void analyzeWatchedStockTestSell() {
        // Given
        MainIndicator expected = new MainIndicator();
        expected.setSuggest(Suggestion.SELL);

        StockSymbol dummyStockSymbol = new StockSymbol(2, "AAA");
        Optional<StockSymbol> stockSymbol = Optional.of(dummyStockSymbol);
        BDDMockito
        .given(symbolRepository.findById(2))
        .willReturn(stockSymbol);

        String mockedReturn = "{\"Time Series (Daily)\": { "
                                + "\"2019-10-05\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.2100) + "\","
                                + "\"2. high\": \"" + new BigDecimal(137.2500)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(135.8100)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(136.0700)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10190307") + "\""
                                + "},"
                             + "\"2019-10-04\": {"
                                + "\"1. open\": \"" +  new BigDecimal(138.0500) + "\","
                                + "\"2. high\": \"" + new BigDecimal(138.2300)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.0400)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(136.0700)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("9469028") + "\""
                                + "},"
                            + "\"2019-10-03\": {"
                                + "\"1. open\": \"" +  new BigDecimal(135.7000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(137.5800)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(135.6000)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(136.9400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("9695164") + "\""
                                + "},"  
                            + "\"2019-10-02\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.2800) + "\","
                                + "\"2. high\": \"" + new BigDecimal(137.8600)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(135.8200)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(135.8800)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10545115") + "\""
                                + "},"
                             + "\"2019-10-01\": {"
                                + "\"1. open\": \"" +  new BigDecimal(136.5600) + "\","
                                + "\"2. high\": \"" + new BigDecimal(138.2700)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(136.5000)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(138.2300)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10238065") + "\""
                                + "},"                   
                            + "\"2019-09-30\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.5000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.2300)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.0800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(138.3400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10386627") + "\""
                                + "},"   
                            + "\"2019-09-29\": {"
                                + "\"1. open\": \"" +  new BigDecimal(139.7900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(141.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(139.2650)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(141.0900)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10802713") + "\""
                                + "},"     
                            + "\"2019-09-28\": {"
                                + "\"1. open\": \"" +  new BigDecimal(138.5100) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.4800)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(138.2950)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.4000)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("7201567") + "\""
                                + "},"                   
                            + "\"2019-09-27\": {"
                                + "\"1. open\": \"" +  new BigDecimal(139.9000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(140.0800)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(138.4700)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(138.6300)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6962744") + "\""
                                + "},"   
                            + "\"2019-09-26\": {"
                                + "\"1. open\": \"" +  new BigDecimal(139.3000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.7700)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(138.6697)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.1400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("7868174") + "\""
                                + "},"                                                                                                             
                            + "\"2019-09-25\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-24\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-23\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-22\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-21\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-20\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-19\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-18\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-17\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"                                                                                                                                                                                                                                                                        
                             + "\"2019-09-16\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""                              
                                + "}}}"                                                                
                                ;
        ResponseEntity<String> response = new ResponseEntity<String>(mockedReturn , HttpStatus.ACCEPTED);
        Mockito.when(restTemplate.getForEntity(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(Class.class)
                ))
             .thenReturn(response);

        // When
        MainIndicator actual = watchedStockService.analyzeWatchedStock(2);

        // Then
        Assert.assertEquals(expected.getSuggest(), actual.getSuggest());
    }

    @Test
    public void analyzeWatchedStockTestBuy() {
        // Given
        MainIndicator expected = new MainIndicator();
        expected.setSuggest(Suggestion.BUY);

        StockSymbol dummyStockSymbol = new StockSymbol(2, "AAA");
        Optional<StockSymbol> stockSymbol = Optional.of(dummyStockSymbol);
        BDDMockito
        .given(symbolRepository.findById(2))
        .willReturn(stockSymbol);

        String mockedReturn = "{\"Time Series (Daily)\": { "
                                + "\"2019-10-05\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.2100) + "\","
                                + "\"2. high\": \"" + new BigDecimal(137.2500)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(135.8100)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(140.0700)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10190307") + "\""
                                + "},"
                             + "\"2019-10-04\": {"
                                + "\"1. open\": \"" +  new BigDecimal(138.0500) + "\","
                                + "\"2. high\": \"" + new BigDecimal(138.2300)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.0400)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(141.0700)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("9469028") + "\""
                                + "},"
                            + "\"2019-10-03\": {"
                                + "\"1. open\": \"" +  new BigDecimal(135.7000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(137.5800)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(135.6000)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.9400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("9695164") + "\""
                                + "},"  
                            + "\"2019-10-02\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.2800) + "\","
                                + "\"2. high\": \"" + new BigDecimal(137.8600)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(135.8200)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(135.8800)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10545115") + "\""
                                + "},"
                             + "\"2019-10-01\": {"
                                + "\"1. open\": \"" +  new BigDecimal(136.5600) + "\","
                                + "\"2. high\": \"" + new BigDecimal(138.2700)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(136.5000)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(138.2300)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10238065") + "\""
                                + "},"                   
                            + "\"2019-09-30\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.5000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.2300)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.0800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(138.3400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10386627") + "\""
                                + "},"   
                            + "\"2019-09-29\": {"
                                + "\"1. open\": \"" +  new BigDecimal(139.7900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(141.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(139.2650)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(141.0900)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("10802713") + "\""
                                + "},"     
                            + "\"2019-09-28\": {"
                                + "\"1. open\": \"" +  new BigDecimal(138.5100) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.4800)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(138.2950)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.4000)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("7201567") + "\""
                                + "},"                   
                            + "\"2019-09-27\": {"
                                + "\"1. open\": \"" +  new BigDecimal(139.9000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(140.0800)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(138.4700)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(138.6300)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6962744") + "\""
                                + "},"   
                            + "\"2019-09-26\": {"
                                + "\"1. open\": \"" +  new BigDecimal(139.3000) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.7700)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(138.6697)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.1400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("7868174") + "\""
                                + "},"                                                                                                             
                            + "\"2019-09-25\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-24\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-23\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-22\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-21\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-20\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"   
                            + "\"2019-09-19\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-18\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"  
                            + "\"2019-09-17\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""
                                + "},"                                                                                                                                                                                                                                                                        
                             + "\"2019-09-16\": {"
                                + "\"1. open\": \"" +  new BigDecimal(137.8900) + "\","
                                + "\"2. high\": \"" + new BigDecimal(139.1000)  + "\","
                                + "\"3. low\": \"" + new BigDecimal(137.7800)  + "\","
                                + "\"4. close\": \"" + new BigDecimal(139.0400)  + "\","
                                + "\"5. volume\": \"" + new BigInteger("6770873") + "\""                              
                                + "}}}"                                                                
                                ;
        ResponseEntity<String> response = new ResponseEntity<String>(mockedReturn , HttpStatus.ACCEPTED);
        Mockito.when(restTemplate.getForEntity(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(Class.class)
                ))
             .thenReturn(response);

        // When
        MainIndicator actual = watchedStockService.analyzeWatchedStock(2);

        // Then
        Assert.assertEquals(expected.getSuggest(), actual.getSuggest());
    }

     @Test
    public void GetRecentStockValuesMockedEndPointTest() {
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
                                + "},"
                             + "\"2019-10-03\": {"
                                + "\"1. open\": \"" + expectedOpen + "\","
                                + "\"2. high\": \"" + expectedHigh + "\","
                                + "\"3. low\": \"" + expectedLow + "\","
                                + "\"4. close\": \"" + expectedClose + "\","
                                + "\"5. volume\": \"" + expectedVol + "\""
                                + "}}}" ;
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
            this.dailyStock.add( dailyStockData[i]);
        }            
    }    

}