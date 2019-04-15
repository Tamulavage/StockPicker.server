package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.model.WatchedStock;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import com.zipcode.stockpicker.repository.WatchedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchedStockService {
    private WatchedStockRepository repository;
    private StockSymbolRepository stockSymbolRepository;

    @Autowired
    public WatchedStockService(WatchedStockRepository repo, StockSymbolRepository stockSymbolRepository) {
        this.repository = repo;
        this.stockSymbolRepository = stockSymbolRepository;
    }

    public WatchedStock watchNewStock(WatchedStock watchedStock) {
        StockSymbol stockSymbol = watchedStock.getStockSymbol();
        StockSymbol existingStock = stockSymbolRepository.findBySymbol(stockSymbol.getSymbol());
        if (existingStock != null) {
            watchedStock.setStockSymbol(existingStock);
        }
        return repository.save(watchedStock);
    }
}
