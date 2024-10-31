package org.capaub.sprintchartjs.repository;


import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class ChartDataRepository {
   private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getStock(Integer companyId) {
        String sql = "CALL getAllProduct(?)";
        return jdbcTemplate.queryForList(sql, companyId);
    }
}