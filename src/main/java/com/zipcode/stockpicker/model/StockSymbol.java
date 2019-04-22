package com.zipcode.stockpicker.model;


import sun.awt.Symbol;

import javax.persistence.*;

@Entity
@Table(name = "stock_symbol")
public class StockSymbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Symbol")
    private String symbol;

    public StockSymbol() {
    }

    public StockSymbol(String symbol) {
        this.symbol = symbol;
    }

    public StockSymbol(Integer id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

//    public StockSymbol(StockSymbol stockSymbol) {
//        this.id = id;
//        this.symbol = symbol;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
