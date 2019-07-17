package com.zipcode.stockpicker.repository;

import com.zipcode.stockpicker.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {
}
