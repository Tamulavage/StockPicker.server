package com.zipcode.stockpicker.repository;

import com.zipcode.stockpicker.model.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockSymbolRepository extends JpaRepository<StockSymbol, Integer> {
    StockSymbol findBySymbol(String string);
    StockSymbol findByName(String string);
}
