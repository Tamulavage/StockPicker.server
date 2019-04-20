package com.zipcode.stockpicker.controller;


import com.zipcode.stockpicker.model.WatchedStock;
import com.zipcode.stockpicker.services.WatchedStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/watch")
public class WatchedStockController {

    private WatchedStockService watchedStockService;

    @Autowired
    public WatchedStockController(WatchedStockService service) {watchedStockService = service;}

    @PostMapping("/new")
    public ResponseEntity<WatchedStock> addWatchedStock(@RequestBody WatchedStock watchedStock){
        return new ResponseEntity<>(watchedStockService.watchNewStock(watchedStock), HttpStatus.CREATED);
    }
}
