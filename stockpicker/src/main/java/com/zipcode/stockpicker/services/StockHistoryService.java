package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.StockHistory;
import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.repository.StockHistoryRepository;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockHistoryService {

    private StockHistoryRepository stockHistoryRepository;
    private StockSymbolRepository stockSymbolRepository;

    @Autowired
    public StockHistoryService(StockHistoryRepository repo, StockSymbolRepository stockSymbolRepository) {
        this.stockHistoryRepository = repo;
        this.stockSymbolRepository = stockSymbolRepository;
    }

    public StockHistory addStockHistory(StockHistory stockHistory) {
        StockSymbol stockSymbol = stockHistory.getStockSymbol();
        StockSymbol existingStock = stockSymbolRepository.findBySymbol(stockSymbol.getSymbol());
        if (existingStock != null) {
            stockHistory.setStockSymbol(existingStock);
        }
        return stockHistoryRepository.save(stockHistory);
    }
}
