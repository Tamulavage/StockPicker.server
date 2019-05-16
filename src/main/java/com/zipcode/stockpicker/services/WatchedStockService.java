package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.model.WatchedStock;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import com.zipcode.stockpicker.repository.WatchedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        watchedStock.setStockSymbol(stockSymbolRepository.findBySymbol(stockSymbol.getSymbol()));
        return repository.save(watchedStock);
    }

    public WatchedStock stopWatchingStock(Integer id) {
        WatchedStock watchedStock = repository.getOne(id);
        if(watchedStock == null){
            throw new IllegalArgumentException("Stock was not watched");
        }
        watchedStock.setEndWatch(LocalDate.now());
        return repository.save(watchedStock);
    }

    public List<WatchedStock> getWatchedStocks() {
        return repository.findAll();
    }

    public void deleteWatchedStocks(Integer id) {
        WatchedStock watchedStock = repository.getOne(id);
        repository.delete(watchedStock);
    }
}
