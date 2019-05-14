package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockSymbolService {

    private StockSymbolRepository stockSymbolRepository;

    @Autowired
    public StockSymbolService(StockSymbolRepository stockSymbolRepository){
        this.stockSymbolRepository = stockSymbolRepository;
    }

    public StockSymbol newStockSymbolByDescription(String symbol){
        StockSymbol newSymbol = new StockSymbol(symbol);
        return stockSymbolRepository.save(newSymbol);
    }

    public StockSymbol newStockSymbolBySymbol(StockSymbol stockSymbol){
        return stockSymbolRepository.save(stockSymbol);
    }

    public void removeStockSymbolById(Integer id){
        stockSymbolRepository.deleteById(id);
    }

    public List<StockSymbol> newAllCurrentStockSymbols() {
        return stockSymbolRepository.findAll();
    }
}
