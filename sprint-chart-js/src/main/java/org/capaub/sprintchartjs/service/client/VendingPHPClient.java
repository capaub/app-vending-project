package org.capaub.sprintchartjs.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(name = "vending-service")
public interface VendingPHPClient {
    @RequestMapping(method = RequestMethod.GET, value = "/php/mongo/chart/{iCompanyId}", consumes = "application/json")
    List<Map<String, Object>> getDataStockOut(@PathVariable Integer iCompanyId);

}