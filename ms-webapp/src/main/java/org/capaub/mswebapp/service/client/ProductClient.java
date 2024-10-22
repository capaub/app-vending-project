package org.capaub.mswebapp.service.client;

import org.capaub.mswebapp.service.dto.BatchDTO;
import org.capaub.mswebapp.service.dto.GoodsDTO;
import org.capaub.mswebapp.service.dto.GoodsWithBatchesInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient("ms-product")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/products/batchesInfo/{companyId}", consumes = "application/json")
    Map<String, GoodsWithBatchesInfoDTO> getProductsForCompany(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.POST, value = "/api/batches/create/{companyId}", consumes = "application/json")
    BatchDTO createBatch(@RequestBody BatchDTO batchDTO, @PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET,value = "/api/goods/findByBarcode/{barcode}/{companyId}", consumes = "application/json")
    GoodsDTO getGoods(@PathVariable String barcode, @PathVariable Integer companyId);
}
