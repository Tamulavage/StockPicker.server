package com.dmt.stockpicker.model.sorters;

import java.util.Comparator;

import com.dmt.stockpicker.model.StockIndicator;

public class SortDailyStockByDay implements Comparator<StockIndicator> {

    @Override
    public int compare(StockIndicator o1, StockIndicator o2) {
        return o2.getLocalDate().compareTo(o1.getLocalDate());
    }

}