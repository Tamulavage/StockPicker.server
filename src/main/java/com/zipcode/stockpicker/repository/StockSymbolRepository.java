package com.zipcode.stockpicker.repository;

import com.zipcode.stockpicker.model.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockSymbolRepository extends JpaRepository<StockSymbol, Integer> {
    StockSymbol findBySymbol(String string);
}
