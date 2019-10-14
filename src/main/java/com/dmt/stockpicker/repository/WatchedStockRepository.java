package com.dmt.stockpicker.repository;

import java.time.LocalDate;
import java.util.List;

import com.dmt.stockpicker.model.WatchedStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedStockRepository extends JpaRepository<WatchedStock, Integer> {
    WatchedStock findByStockSymbolId(Integer integer);
    List<WatchedStock> findByEndWatch(LocalDate endWatch);
}
