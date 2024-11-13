package org.capaub.sprintchartjs.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(name = "vending-service")
public interface VendingPHPClient {
    @RequestMapping(method = RequestMethod.GET, value = "/php/mongo/chart/extTotalProduct/{companyId}", consumes = "application/json")
    List<Map<String, Object>> getDataStockOut(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/php/mongo/chart/vendingStock/{vendingId}", consumes = "application/json")
    List<Map<String, Object>> getDataVendingStock(@PathVariable String vendingId);

}