package com.dmt.stockpicker.services;

import org.json.*;

import com.dmt.stockpicker.model.DailyStockData;
import com.dmt.stockpicker.model.Indicator;
import com.dmt.stockpicker.model.StockSymbol;
import com.dmt.stockpicker.model.WatchedStock;
import com.dmt.stockpicker.repository.StockSymbolRepository;
import com.dmt.stockpicker.repository.WatchedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

@Service
public class WatchedStockService {
    private WatchedStockRepository repository;
    private StockSymbolRepository stockSymbolRepository;

    private RestTemplate restTemplate ;


    @Autowired
    public WatchedStockService(WatchedStockRepository repo, StockSymbolRepository stockSymbolRepository, RestTemplate restTemplate) {
        this.repository = repo;
        this.stockSymbolRepository = stockSymbolRepository;
        this.restTemplate = restTemplate;
    }

    public WatchedStock watchNewStock(WatchedStock watchedStock) {
        StockSymbol stockSymbol = watchedStock.getStockSymbol();
        watchedStock.setStockSymbol(stockSymbolRepository.findBySymbol(stockSymbol.getSymbol()));
        if(watchedStock.getStockSymbol()==null){
            watchedStock.setStockSymbol(stockSymbolRepository.save(stockSymbol));
        }
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

    public WatchedStock stopWatchingStock(String stockName) {
        StockSymbol stockSymbol = stockSymbolRepository.findBySymbol(stockName);
        WatchedStock watchedStock = repository.findByStockSymbolId(stockSymbol.getId());
        // WatchedStock watchedStock = new WatchedStock();
        watchedStock.setEndWatch(LocalDate.now());
        return repository.save(watchedStock);
        // return null;
	}

    public List<WatchedStock> getWatchedStocks() {
        return repository.findAll();
    }

    public void deleteWatchedStocks(Integer id) {
        WatchedStock watchedStock = repository.getOne(id);
        repository.delete(watchedStock);
    }

    public Indicator analyzeWatchedStock(Integer id){

        try{
          StockSymbol stockSymbol = getStockSymbolById(id);
          TreeMap<Date,DailyStockData>  dailyStock = getRecentStockValues(stockSymbol.getSymbol());
          Indicator indicator = getIndicator(dailyStock, stockSymbol);

          return indicator;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    private Indicator getIndicator(TreeMap<Date,DailyStockData> dailyStock, StockSymbol stock) {
        // TODO: Run indicator here: Remove hard coded values
        Indicator indicator = new Indicator();
        indicator.setIndicatorStrength(50);
        indicator.setResistanceLine(new BigDecimal("100.5"));
        indicator.setSupportLine(new BigDecimal("100"));
        indicator.setSuggestedAction("Stay");

        indicator.setStock(stock);

        return indicator;
    }

    private StockSymbol getStockSymbolById(Integer id) throws Exception{

        Optional<StockSymbol> stockSymbol =  stockSymbolRepository.findById(id);
        if(stockSymbol.isPresent()){
         return stockSymbol.get();
        }
        else{
            throw new Exception("Not valid Stock id");
        }
    }

    public TreeMap<Date,DailyStockData>  getRecentStockValues(String stockName){
        
        String apiUri =  "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
        String apiKey = "&apikey=HNX418W2U7ARUI2F";
        TreeMap<Date,DailyStockData>  stockValuesLastHundred = new TreeMap<Date,DailyStockData>();

        String apiUrl = apiUri+stockName+apiKey;

         ResponseEntity<String> response =
             restTemplate.getForEntity(apiUrl, String.class);

          JSONObject timeDailyDataAll = new JSONObject(response.getBody());
          JSONObject timeSeries = timeDailyDataAll.getJSONObject("Time Series (Daily)");
          Iterator<String> keys = timeSeries.keys();
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

          while(keys.hasNext()){
            String stringDateOfData = (String)keys.next();
            try{
                Date date = format.parse(stringDateOfData);

                JSONObject valueOfData = timeSeries.getJSONObject(stringDateOfData);

                BigDecimal openAmount = new BigDecimal(valueOfData.get("1. open").toString());
                BigDecimal closeAmount = new BigDecimal(valueOfData.get("4. close").toString());
                BigDecimal highAmount = new BigDecimal(valueOfData.get("2. high").toString());
                BigDecimal lowAmount = new BigDecimal(valueOfData.get("3. low").toString());
                BigInteger volume = new BigInteger(valueOfData.get("5. volume").toString());

                DailyStockData dailyStockData = new DailyStockData(openAmount, 
                    closeAmount, 
                    highAmount, 
                    lowAmount, 
                    volume);

                stockValuesLastHundred.put(date, dailyStockData);
            }
            catch (Exception e) {
                e.getMessage();
            }
          }

        return stockValuesLastHundred;
    }


}
