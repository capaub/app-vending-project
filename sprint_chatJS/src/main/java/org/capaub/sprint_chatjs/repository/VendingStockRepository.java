package org.capaub.sprint_chatjs.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class VendingStockRepository {
   private final JdbcTemplate jdbcTemplate;

    public VendingStockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getAllVendingStock(Integer vendingId) {
        String sql = "CALL getAllProductByVenidngId(?)";
        return jdbcTemplate.queryForList(sql, vendingId);
   }
}