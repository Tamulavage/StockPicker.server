package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.History;
import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.model.WatchedStock;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import com.zipcode.stockpicker.repository.WatchedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public List<WatchedStock> getRecentStockValues(Integer id){
        RestTemplate restTemplate = new RestTemplate();
        String apiUri =  "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
        String apiKey = "&apikey=HNX418W2U7ARUI2F";
        String stockShort = "JPM";

        String apiUrl = apiUri+stockShort+apiKey;

        ResponseEntity<String> response =
            restTemplate.getForEntity(apiUrl, String.class);

        System.out.println(response.getBody());

        History history =
         restTemplate.getForObject(apiUrl, History.class);

       //  System.out.println(history.getHighAmount());

        return null;
    }
}
