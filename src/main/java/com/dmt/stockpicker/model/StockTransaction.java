package com.dmt.stockpicker.model;

import javax.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "stock_transaction")
public class StockTransaction{ 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "stock_symbol_id")
    private StockSymbol stockSymbol;

    @Column(name = "buy_price")
    private BigInteger buyPrice;

    @Column(name = "sell_price")
    private BigInteger sellPrice;

    @Column(name = "buy_dt")
    private Date buyDate;
    
    @Column(name = "sell_dt")
    private Date sellDate;

    @Column(name = "qty")
    private BigInteger quantity;

    public StockTransaction() {}

    public StockTransaction(StockSymbol stockSymbol, Date buyDate, Date sellDate,
         BigInteger buyPrice, BigInteger sellPrice, BigInteger qty){
             this.stockSymbol = stockSymbol;
             this.buyDate = buyDate;
             this.sellDate = sellDate;
             this.buyPrice = buyPrice;
             this.sellPrice = sellPrice;
             this.quantity = qty;
     }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public Date getSellDate(){
        return this.sellDate;
    }

    public void setQuantity(BigInteger qty) {
        this.quantity = qty;
    }

    public BigInteger getQuantity(){
        return this.quantity;
    }

    public void setBuyDate(Date buyDate){
        this.buyDate = buyDate;
    }

    public Date getBuyDate(){
        return this.buyDate;
    }

    public StockSymbol getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(StockSymbol stockSymbol) {
        this.stockSymbol = stockSymbol;
    } 

    public void setBuyPrice(BigInteger buyPrice){
        this.buyPrice = buyPrice;
    }

    public BigInteger getBuyPrice(){
        return this.buyPrice;
    }

    public void setSellPrice(BigInteger sellPrice){
        this.sellPrice = sellPrice;
    }

    public BigInteger getSellPrice(){
        return this.sellPrice;
    }
}
