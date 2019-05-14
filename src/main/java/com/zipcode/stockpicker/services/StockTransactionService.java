package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.StockTransaction;
import com.zipcode.stockpicker.repository.StockTransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockTransactionService {

    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    public StockTransactionService(StockTransactionRepository stockTransactionRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
    }

    public StockTransaction newStockTransaction(StockTransaction stockTransaction){
        return stockTransactionRepository.save(stockTransaction);
    }
    
}