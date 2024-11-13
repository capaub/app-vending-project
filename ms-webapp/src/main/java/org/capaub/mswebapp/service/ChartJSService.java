package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.client.ChartJsClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChartJSService {

    private final ChartJsClient chartJsClient;

    public String getChartStockIn(Integer companyId) {
        return chartJsClient.getChartDataStockIn(companyId);
    }

    public String getChartStockOut(Integer companyId) {
        return chartJsClient.getChartDataStockOut(companyId);
    }

    public String getDataVendingStock(String vendingId) {
        return chartJsClient.getDataVendingStock(vendingId);
    }
}