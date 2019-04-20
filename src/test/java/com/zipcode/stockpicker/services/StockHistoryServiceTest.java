package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.StockHistory;
import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.repository.StockHistoryRepository;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StockHistoryServiceTest {

    @MockBean
    private StockHistoryRepository stockHistoryRepository;

    @MockBean
    private StockSymbolRepository symbolRepository;

    @Autowired
    private StockHistoryService stockHistoryService;

    @Before
    public void setup(){
        this.stockHistoryRepository = mock(StockHistoryRepository.class);
        this.symbolRepository = mock(StockSymbolRepository.class);
        this.stockHistoryService = new StockHistoryService(stockHistoryRepository, symbolRepository);

    }

    @Test
    public void testAddStockHistory(){
        String symbol = "test";
        StockSymbol stockSymbol = new StockSymbol(1, symbol);
        StockHistory stockHistory = new StockHistory(0.1, 0.1, 0.1,
                0.1,  10, null, null, stockSymbol );
        StockHistory stockHistoryExpected = new StockHistory(0.1, 0.1, 0.1,
                0.1,  10, null, null, stockSymbol );

        when(symbolRepository.findBySymbol(symbol)).thenReturn(stockSymbol);
        when(stockHistoryRepository.save(stockHistory)).thenReturn(stockHistoryExpected);

        StockHistory actual = stockHistoryService.addStockHistory(stockHistory);

        Assert.assertEquals(stockHistoryExpected,actual );

    }

}