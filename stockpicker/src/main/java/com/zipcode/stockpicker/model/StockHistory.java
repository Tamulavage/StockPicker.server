package com.zipcode.stockpicker.model;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "stock_history")
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name="open_amt")
    private Double openAmount;

    @Column(name="Close_amt")
    private Double closeAmount;

    @Column(name="High_amt")
    private Double highAmount;

    @Column(name="Low_amt")
    private Double lowAmount;

    @Column(name="Volume")
    private Integer volume;

    @Column(name="time_frame_Unit")
    private Integer timeFrameUnit;

    @Column(name="time_Frame_Type")
    private String timeFrameType;

    @ManyToOne(cascade = ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "stock_symbol_id")
    private StockSymbol stockSymbol;

    public StockHistory() {}

    public StockHistory(Double openAmount, Double closeAmount, Double highAmount, Double lowAmount, Integer volume,
                        Integer timeFrameUnit, String timeFrameType, StockSymbol stockSymbol) {
        this.openAmount = openAmount;
        this.closeAmount = closeAmount;
        this.highAmount = highAmount;
        this.lowAmount = lowAmount;
        this.volume = volume;
        this.timeFrameUnit = timeFrameUnit;
        this.timeFrameType = timeFrameType;
        this.stockSymbol = stockSymbol;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Double getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(Double openAmount) {
        this.openAmount = openAmount;
    }

    public Double getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(Double closeAmount) {
        this.closeAmount = closeAmount;
    }

    public Double getHighAmount() {
        return highAmount;
    }

    public void setHighAmount(Double highAmount) {
        this.highAmount = highAmount;
    }

    public Double getLowAmount() {
        return lowAmount;
    }

    public void setLowAmount(Double lowAmount) {
        this.lowAmount = lowAmount;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getTimeFrameUnit() {
        return timeFrameUnit;
    }

    public void setTimeFrameUnit(Integer timeFrameUnit) {
        this.timeFrameUnit = timeFrameUnit;
    }

    public String getTimeFrameType() {
        return timeFrameType;
    }

    public void setTimeFrameType(String timeFrameType) {
        this.timeFrameType = timeFrameType;
    }

    public StockSymbol getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(StockSymbol stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
}
