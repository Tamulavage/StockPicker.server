package com.dmt.stockpicker.controller;

import com.dmt.stockpicker.model.IndicatorData;
import com.dmt.stockpicker.model.MainIndicator;
import com.dmt.stockpicker.model.WatchedStock;
import com.dmt.stockpicker.services.WatchedStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/watch")
public class WatchedStockController {

    private WatchedStockService watchedStockService;

    @Autowired
    public WatchedStockController(WatchedStockService service) {
        watchedStockService = service;
    }

    @PostMapping("/new")
    public ResponseEntity<WatchedStock> addWatchedStock(@RequestBody WatchedStock watchedStock) {
        try{
            return new ResponseEntity<>(watchedStockService.watchNewStock(watchedStock), HttpStatus.CREATED);
        }
        catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<WatchedStock>> getWatchedStock() {
        return new ResponseEntity<>(watchedStockService.getWatchedStocks(), HttpStatus.OK);
    }

    @GetMapping("/analyzeWatchedStocks/")
    public ResponseEntity<List<IndicatorData>> analyzeWatchedStocks() {
        // return new ResponseEntity<>(watchedStockService.analyzeWatchedStocks(),
        // HttpStatus.OK);
        // TODO: complete once single is populated
        return null;
    }

    @GetMapping("/analyzeWatchedStock/")
    public ResponseEntity<MainIndicator> analyzeWatchedStocks(String symbol, Integer slowEMA, Integer fastEMA ) {
        try{
            System.out.println("Method analyzeWatchedStocks started with symbol "+ symbol);
            return new ResponseEntity<>(watchedStockService.analyzeWatchedStock(symbol, slowEMA, fastEMA ) , HttpStatus.OK);
        }
        catch(Exception e){
            e.getMessage();
            e.printStackTrace();
            return new ResponseEntity<>(null , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WatchedStock> deleteWatchedStock(@PathVariable Integer id) {
        // return new ResponseEntity<>(watchedStockService.deleteWatchedStocks(id),
        // HttpStatus.OK);
        watchedStockService.deleteWatchedStocks(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/end/{name}")
    public ResponseEntity<WatchedStock> updateEndDate(@PathVariable String name) {
        try{
            return new ResponseEntity<>(watchedStockService.stopWatchingStock(name), HttpStatus.OK);
        }
        catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
