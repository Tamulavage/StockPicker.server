package com.zipcode.stockpicker.services;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;

import org.apache.tomcat.util.json.JSONParser;
import org.json.*;
import com.zipcode.stockpicker.model.History;
import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.model.WatchedStock;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import com.zipcode.stockpicker.repository.WatchedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

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
        TreeMap stockValuesLastHundred = new TreeMap();

        String apiUrl = apiUri+stockShort+apiKey;

         ResponseEntity<String> response =
             restTemplate.getForEntity(apiUrl, String.class);

          JSONObject timeDailyDataAll = new JSONObject(response.getBody());
          JSONObject timeSeries = timeDailyDataAll.getJSONObject("Time Series (Daily)");
          Iterator keys = timeSeries.keys();
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

          while(keys.hasNext()){
            String stringDateOfData = (String)keys.next();
            System.out.println(stringDateOfData);
            try{
                Date date = format.parse(stringDateOfData);
                stockValuesLastHundred.put(date, "test");
                JSONObject valueOfData = timeSeries.getJSONObject(stringDateOfData);

                // TODO: Move from SOUTs to actual object, then pass the map back
                System.out.println(valueOfData.get("1. open").toString());
                System.out.println(valueOfData.get("2. high").toString());
                System.out.println(valueOfData.get("3. low").toString());
                System.out.println(valueOfData.get("4. close").toString());
                System.out.println(valueOfData.get("5. volume").toString());
            }
            catch (Exception e) {}

          }

        return null;
    }
}
