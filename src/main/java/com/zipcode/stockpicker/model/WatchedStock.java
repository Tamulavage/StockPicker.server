package com.zipcode.stockpicker.model;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "watched_stocks")
public class WatchedStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;
    @Column(name = "start_watch")
    private LocalDate startWatch;
    @Column(name = "end_watch")
    private LocalDate endWatch;

    @ManyToOne(cascade = PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name= "stock_symbol_id")
    private StockSymbol stockSymbol;

    public WatchedStock() {}

    public WatchedStock(LocalDate startWatch, LocalDate endWatch, StockSymbol stockSymbol) {
        this.startWatch = startWatch;
        this.endWatch = endWatch;
        this.stockSymbol = stockSymbol;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public LocalDate getStartWatch() {
        return startWatch;
    }

    public void setStartWatch(LocalDate startWatch) {
        this.startWatch = startWatch;
    }

    public LocalDate getEndWatch() {
        return endWatch;
    }

    public void setEndWatch(LocalDate endWatch) {
        this.endWatch = endWatch;
    }

    public StockSymbol getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(StockSymbol stockSymbol) {
        this.stockSymbol = stockSymbol;
    }


}
