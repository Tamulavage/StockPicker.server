package com.dmt.stockpicker.services;

import java.util.List;

import com.dmt.stockpicker.model.StockSymbol;
import com.dmt.stockpicker.model.StockTransaction;
import com.dmt.stockpicker.repository.StockTransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockTransactionService {

    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    public StockTransactionService(StockTransactionRepository stockTransactionRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
    }

    public List<StockTransaction> getAllTradedStocks() {
        return stockTransactionRepository.findAll();
    }

    public List<StockTransaction> getTradedStocksByStock(Integer id) {
        return null;
    }

    public List<StockTransaction> getCurrentTradedStocks() {
        return null;
    }

    public List<StockTransaction> getCurrentTradedStocksByStock(Integer id) {
        return null;
    }

    public StockTransaction newStockTrade(StockTransaction stockTransaction){
        return stockTransactionRepository.save(stockTransaction);
    }

    public StockTransaction sellStockTransaction(StockTransaction stockTransaction){

        StockSymbol stockSymbol = stockTransaction.getStockSymbol();
        if(userHasCurrentStock(stockSymbol)){

        }
        else {
             stockTransaction.setStockSymbol(stockSymbol);
             stockTransactionRepository.save(stockTransaction);
        }

  //      StockTransaction transactionFromDB = stockTransactionRepository.findBySymbol(
    //            stockTransaction.getStockSymbol().getSymbol());

        return stockTransaction;
    }

    private boolean userHasCurrentStock(StockSymbol stockSymbol){
        return false;
    }

    // TODO: MVP Is to sell/buy all in batch - need to break in future
}