package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.model.WatchedStock;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import com.zipcode.stockpicker.repository.WatchedStockRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WatchedStockServiceTest {

    @MockBean
    private WatchedStockRepository watchedStockRepository;

    @MockBean
    private StockSymbolRepository symbolRepository;

    @Autowired
    private WatchedStockService watchedStockService;

    @Before
    public void setup(){
        this.watchedStockRepository = mock(WatchedStockRepository.class);
        this.symbolRepository = mock(StockSymbolRepository.class);
        this.watchedStockService = new WatchedStockService( watchedStockRepository, symbolRepository);

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


}