package org.capaub.sprintchartjs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.capaub.sprintchartjs.repository.ChartDataRepository;
import org.capaub.sprintchartjs.service.client.VendingPHPClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ChartJsService {
    private final ChartDataRepository chartDataRepository;
    private final VendingPHPClient vendingPHPClient;

    public String convertListMapToJson(List<Map<String, Object>> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDataStockIn(Integer companyId)
    {
        List<Map<String, Object>> stockIn = chartDataRepository.getStock(companyId);
        return convertListMapToJson(stockIn);
    }

    public String getDataStockOut(Integer companyId)
    {
        List<Map<String, Object>> stockOut = vendingPHPClient.getDataStockOut(companyId);
        return convertListMapToJson(stockOut);
    }
}