package com.zipcode.stockpicker.repository;

import com.zipcode.stockpicker.model.WatchedStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedStockRepository extends JpaRepository<WatchedStock, Integer> {
}
