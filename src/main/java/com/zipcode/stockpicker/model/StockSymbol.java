package com.zipcode.stockpicker.model;

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

    @Column(name = "name")
    private String name;

    public StockSymbol() {
    }

    public StockSymbol(String symbol) {
        this.symbol = symbol;
    }

    public StockSymbol(Integer id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
