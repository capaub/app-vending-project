package org.capaub.mswebapp.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("sprint-chart-js")
public interface ChartJsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/data/generalStock/{companyId}", consumes = "application/json")
    String getChartDataStockIn(@PathVariable("companyId") Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/data/stockOut/{companyId}", consumes = "application/json")
    String getChartDataStockOut(@PathVariable("companyId") Integer companyId);
}