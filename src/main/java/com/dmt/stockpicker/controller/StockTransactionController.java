package com.dmt.stockpicker.controller;

import java.util.List;

import com.dmt.stockpicker.model.StockTransaction;
import com.dmt.stockpicker.services.StockTransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/trade")
public class StockTransactionController {
    
    private StockTransactionService stockTransactionService;

    @Autowired
    public StockTransactionController(StockTransactionService stockTransactionService){
        this.stockTransactionService = stockTransactionService;
    }

    @PostMapping("/new")
    public ResponseEntity<StockTransaction> addNewTrade(@RequestBody StockTransaction stockTransaction){
        return new ResponseEntity<>(stockTransactionService.newStockTrade(stockTransaction), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<StockTransaction>> getTradedStocks(){
        return new ResponseEntity<>(stockTransactionService.getAllTradedStocks(), HttpStatus.OK);
    }
}