package com.dmt.stockpicker.repository;

import com.dmt.stockpicker.model.WatchedStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedStockRepository extends JpaRepository<WatchedStock, Integer> {
    WatchedStock findByStockSymbolId(Integer integer);
}
