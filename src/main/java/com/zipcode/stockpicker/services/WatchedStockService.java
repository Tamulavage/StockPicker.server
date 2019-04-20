package com.zipcode.stockpicker.services;

import com.zipcode.stockpicker.model.WatchedStocks;
import com.zipcode.stockpicker.repository.WatchedStockRepository;
import org.springframework.stereotype.Service;

@Service
public class WatchedStocksService {
    private WatchedStockRepository repository;

    public WatchedStocks watchNewStock(    @Column(name = "end_watch")
                                           private LocalDate endWatch;

                                                   @OneToOne(cascade = ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fk_stock")
    @Column(name="stock_symbol_id")
    private StockSymbol stockSymbol;

    public WatchedStocks () {}

    public WatchedStocks(LocalDate startWatch, LocalDate endWatch, StockSymbol stockSymbol) {
        this.startWatch = startWatch;
        this.endWatch = endWatch;
        this.stockSymbol = stockSymbol;
    })
}
