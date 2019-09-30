package com.dmt.stockpicker.repository;

import com.dmt.stockpicker.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
}
