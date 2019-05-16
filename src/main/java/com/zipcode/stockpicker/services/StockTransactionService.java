package com.zipcode.stockpicker.services;

import java.util.List;

import com.zipcode.stockpicker.model.StockSymbol;
import com.zipcode.stockpicker.model.StockTransaction;
import com.zipcode.stockpicker.repository.StockSymbolRepository;
import com.zipcode.stockpicker.repository.StockTransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockTransactionService {

    private StockTransactionRepository stockTransactionRepository;
    private StockSymbolRepository stockSymbolRepository;

    @Autowired
    public StockTransactionService(StockTransactionRepository stockTransactionRepository,
        StockSymbolRepository stockSymbolRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
        this.stockSymbolRepository = stockSymbolRepository;
    }

    public List<StockTransaction> getTradedStocks() {
        return stockTransactionRepository.findAll();
    }

    public StockTransaction newStockTransaction(StockTransaction stockTransaction){
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

    private StockTransaction[] getCurrenTransactions(){
        return null;
    }

    // todo: MVP Is to sell/buy all in batch - need to break in future
}