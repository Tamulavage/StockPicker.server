package com.zipcode.stockpicker.model;

import javax.persistence.*;
import static javax.persistence.CascadeType.ALL;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "stock_transaction")
public class StockTransaction{ 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(cascade = ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "stock_symbol_id")
    private StockSymbol stockSymbol;

    @Column(name = "buy_price")
    BigInteger buyPrice;

    @Column(name = "sell_price")
    BigInteger sellPrice;

    @Column(name = "buy_dt")
    Date buyDate;
    
    @Column(name = "sell_dt")
    Date sellDate;

    @Column(name = "qty")
    BigInteger quantity;

    public StockTransaction(StockSymbol stockSymbol, Date buyDate, Date sellDate,
         BigInteger buyPrice, BigInteger sellPrice, BigInteger qty){
             this.stockSymbol = stockSymbol;
             this.buyDate = buyDate;
             this.sellDate = sellDate;
             this.buyPrice = buyPrice;
             this.sellPrice = sellPrice;
             this.quantity = qty;
         }
}
