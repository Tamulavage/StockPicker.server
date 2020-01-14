package com.dmt.stockpicker.services;

import org.json.*;

import com.dmt.stockpicker.configuration.Formulas;
import com.dmt.stockpicker.enums.Suggestion;
import com.dmt.stockpicker.model.IndicatorData;
import com.dmt.stockpicker.model.MainIndicator;
import com.dmt.stockpicker.model.StockIndicator;
import com.dmt.stockpicker.model.StockSymbol;
import com.dmt.stockpicker.model.WatchedStock;
import com.dmt.stockpicker.model.sorters.SortDailyStockByDay;
import com.dmt.stockpicker.repository.StockSymbolRepository;
import com.dmt.stockpicker.repository.WatchedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class WatchedStockService {
    
    final BigDecimal zero = new BigDecimal("0");
    final BigDecimal two = new BigDecimal("2");
    final BigDecimal three = new BigDecimal("2");

    @Autowired
    private WatchedStockRepository repository;
    @Autowired
    private StockSymbolRepository stockSymbolRepository;

    @Autowired
    private RestTemplate restTemplate; 

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
        // WatchedStock watchedStock = repository.findByStockSymbolId(stockSymbol.getId());

        WatchedStock watchedStock = repository.findByStockSymbolIdAndEndWatchNull(stockSymbol.getId());
        watchedStock.setEndWatch(LocalDate.now());
        return repository.save(watchedStock);
	}

    public List<WatchedStock> getWatchedStocks() {
        return repository.findByEndWatch(null);
    }

    public void deleteWatchedStocks(Integer id) {
        WatchedStock watchedStock = repository.getOne(id);
        repository.delete(watchedStock);
    }

    public MainIndicator analyzeWatchedStock(Integer id, Integer slowEMA, Integer fastEMA){

        try{
          StockSymbol stockSymbol = getStockSymbolById(id);
          ArrayList<StockIndicator> dailyStock = getRecentStockValues(stockSymbol.getSymbol());
          dailyStock = populateRawIndicatorData(dailyStock, slowEMA, fastEMA);
          MainIndicator indicator = getCurentIndicator(dailyStock);

          return indicator;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<StockIndicator> populateRawIndicatorData(ArrayList<StockIndicator> dailyStock,
                                                            Integer slowEMA,
                                                            Integer fastEMA){
        List<StockIndicator> dailyIndicators = populateTrendLines(dailyStock);
        dailyIndicators = populateMACD(dailyIndicators, slowEMA, fastEMA);

        // dailyIndicators.forEach((x) -> System.out.println(x));
        return (ArrayList<StockIndicator>) dailyIndicators;
    }

    private List<StockIndicator> populateMACD(List<StockIndicator> dailyIndicators, Integer slowEMA, Integer fastEMA){
        if(slowEMA==null){
            slowEMA=10;
        }
        if(fastEMA==null){
            fastEMA=5;
        }
        dailyIndicators = Formulas.populateExponentialMovingAverage(fastEMA, dailyIndicators, true);
        dailyIndicators = Formulas.populateExponentialMovingAverage(slowEMA, dailyIndicators, false); 
        dailyIndicators = Formulas.populateMACD(dailyIndicators);         

        return dailyIndicators;
    }

    private MainIndicator getCurentIndicator(ArrayList<StockIndicator>  dailyStock) {        
        
        // TODO: Run indicator here:
        MainIndicator indicator = new MainIndicator();
        if(dailyStock.get(0).getIndicator().getMACD().compareTo(zero)>0)
            indicator.setSuggest(Suggestion.BUY);
        else if(dailyStock.get(0).getIndicator().getMACD().compareTo(zero)<0)
            indicator.setSuggest(Suggestion.SELL);

        return indicator;
    }

    private BigDecimal idPivotPoint(StockIndicator pivotRawData){
        // Pivot point (PP) = (High + Low + Close) / 3
        BigDecimal pivotPoint = zero;
        pivotPoint = pivotPoint.add(pivotRawData.getHighAmount());
        pivotPoint = pivotPoint.add(pivotRawData.getLowAmount());
        pivotPoint = pivotPoint.add(pivotRawData.getCloseAmount());
        // greater then 0, OK to Divide
        if(pivotPoint.compareTo(zero) == 1){
            pivotPoint = pivotPoint.divide(three, RoundingMode.CEILING);
        }
        return pivotPoint;
        // other P options
        //  P = (O + H + L + C) / 4
        //  P = ((Current O) + Previous(H + L + C)) / 4
        //  P = ((Today’s Open) + Yesterday’s (H + L + C)) / 4
    }

    private BigDecimal idSupportLine(BigDecimal pivotPoint, BigDecimal highAmount) {
        // First support (S1) = (2 x PP) – High.
        return two.multiply(pivotPoint).subtract(highAmount);
    }

    private BigDecimal idResistanceLine(BigDecimal pivotPoint, BigDecimal lowAmount) {
        // First resistance (R1) = (2 x PP) – Low.
        return two.multiply(pivotPoint).subtract(lowAmount);
    }

    public ArrayList<StockIndicator> populateTrendLines(ArrayList<StockIndicator>  dailyStock){        
        Integer dataSetSize = dailyStock.size();

        for(int i=0; i<dataSetSize-1; i++){
            IndicatorData indicator = new IndicatorData();

            indicator.setPivotPoint(idPivotPoint(dailyStock.get(i+1)));
            indicator.setSupportLine(idSupportLine(indicator.getPivotPoint(), dailyStock.get(i+1).getHighAmount()));
            indicator.setResistanceLine(idResistanceLine(indicator.getPivotPoint(), dailyStock.get(i+1).getLowAmount()));
        
            dailyStock.get(i).setIndicator(indicator);
        }
    
        // TODO: r2/p2, r3/p3
        // Second resistance (R2) = PP + (High – Low) Second support (S2) = PP – (High – Low)
        // Third resistance (R3) = High + 2(PP – Low) Third support (S3) = Low – 2(High – PP)
        return dailyStock;
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

    public ArrayList<StockIndicator>  getRecentStockValues(String stockName){        
        
        String apiUri =  "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
        String apiKey = "&apikey=HNX418W2U7ARUI2F";
        ArrayList<StockIndicator> stockValuesLastHundred = new ArrayList<StockIndicator>();

        String apiUrl = apiUri+stockName+apiKey;

         ResponseEntity<String> response =
             restTemplate.getForEntity(apiUrl, String.class);

          JSONObject timeDailyDataAll = new JSONObject(response.getBody());
          JSONObject timeSeries = timeDailyDataAll.getJSONObject("Time Series (Daily)");
          Iterator<String> keys = timeSeries.keys();
          DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

          while(keys.hasNext()){
            String stringDateOfData = (String)keys.next();
            try{
                LocalDate date = LocalDate.parse(stringDateOfData, format);

                JSONObject valueOfData = timeSeries.getJSONObject(stringDateOfData);

                BigDecimal openAmount = new BigDecimal(valueOfData.get("1. open").toString());
                BigDecimal closeAmount = new BigDecimal(valueOfData.get("4. close").toString());
                BigDecimal highAmount = new BigDecimal(valueOfData.get("2. high").toString());
                BigDecimal lowAmount = new BigDecimal(valueOfData.get("3. low").toString());
                BigInteger volume = new BigInteger(valueOfData.get("5. volume").toString());

                StockIndicator stockData = new StockIndicator(openAmount, 
                    closeAmount, 
                    highAmount, 
                    lowAmount, 
                    volume,
                    date);

                stockValuesLastHundred.add(stockData);
                
            }
            catch (Exception e) {
                e.getMessage();
            }
          }

        stockValuesLastHundred.sort(new SortDailyStockByDay());
        return stockValuesLastHundred;
    }
}
