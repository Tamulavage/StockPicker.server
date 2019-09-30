package com.dmt.stockpicker.controller;

import com.dmt.stockpicker.model.StockSymbol;
import com.dmt.stockpicker.services.StockSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/symbol")
public class StockSymbolController {

    private StockSymbolService stockSymbolService;

    @Autowired
    public StockSymbolController(StockSymbolService stockSymbolService){
        this.stockSymbolService = stockSymbolService;
    }

    @GetMapping("/")
    public ResponseEntity<List<StockSymbol>> getAllCurrentStockSymbols(){
        return new ResponseEntity<>(stockSymbolService.newAllCurrentStockSymbols(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<StockSymbol> addNewStockSymbol(@RequestBody StockSymbol stockSymbol){
        return new ResponseEntity<>(stockSymbolService.newStockSymbolBySymbol(stockSymbol), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StockSymbol> deleteStockSymbol(@PathVariable Integer id){
        stockSymbolService.removeStockSymbolById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
