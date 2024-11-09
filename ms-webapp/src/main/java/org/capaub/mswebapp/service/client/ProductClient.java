package org.capaub.mswebapp.service.client;

import org.capaub.mswebapp.service.dto.BatchDTO;
import org.capaub.mswebapp.service.dto.GoodsDTO;
import org.capaub.mswebapp.service.dto.GoodsWithBatchesInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient("ms-product")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/products/batchesInfo/{companyId}", consumes = "application/json")
    Map<String, GoodsWithBatchesInfoDTO> getProductsForCompany(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/api/products/checkAvailableQuantity/{batchId}/{quantity}", consumes = "application/json")
    Boolean checkAvailableQuantity(@PathVariable Integer batchId, @PathVariable Integer quantity);

    @RequestMapping(method = RequestMethod.POST, value = "/api/products/decreaseQuantity/{batchId}/{quantityToReduce}", consumes = "application/json")
    void decreaseQuantity(@PathVariable Integer batchId, @PathVariable Integer quantityToReduce);

    @RequestMapping(method = RequestMethod.POST,value = "/api/products/increaseQuantity/{batchId}/{quantityToAdd }", consumes = "application/json")
    void increaseQuantity(@PathVariable Integer batchId, @PathVariable Integer quantityToAdd);

    @RequestMapping(method = RequestMethod.POST, value = "/api/batches/create/{companyId}", consumes = "application/json")
    BatchDTO createBatch(@RequestBody BatchDTO batchDTO, @PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/api/batches/allByCompanyId/{companyId}", consumes = "application/json")
    List<BatchDTO> getAllBatch(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/api/batches/getAllFiltered/{companyId}", consumes = "application/json")
    List<BatchDTO> getAllFilteredBatch(@PathVariable Integer companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/api/batches/get/{batchId}", consumes = "application/json")
    BatchDTO getBatch(@PathVariable Integer batchId);

    @RequestMapping(method = RequestMethod.GET,value = "/api/goods/findByBarcode/{barcode}/{companyId}", consumes = "application/json")
    GoodsDTO getGoods(@PathVariable String barcode, @PathVariable Integer companyId);

}