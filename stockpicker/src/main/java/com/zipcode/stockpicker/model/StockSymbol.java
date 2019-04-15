package com.zipcode.stockpicker.model;


import javax.persistence.*;

@Entity
@Table(name = "stock_symbol")
public class StockSymbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name="Symbol")
    private String symbol;

    public StockSymbol() {}

    public StockSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
