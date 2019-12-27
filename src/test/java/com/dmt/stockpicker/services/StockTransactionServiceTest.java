package com.dmt.stockpicker.services;

import com.dmt.stockpicker.repository.StockTransactionRepository;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StockTransactionServiceTest {
    
    @Mock
    private StockTransactionRepository stockTransactionRepository;

    private StockTransactionService stockSymbolService;

    @Before
    public void setUp() {
        this.stockSymbolService = new StockTransactionService(stockTransactionRepository);
    }

    @Ignore
    @Test
    public void sellStockTransactionTest() {
        Assert.assertFalse(true);
    }
}