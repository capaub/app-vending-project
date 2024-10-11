package org.capaub.msproduct.service.client;

import org.capaub.msproduct.service.dto.GoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ms-external-api")
public interface ExternalApiClient {
    @RequestMapping(method = RequestMethod.GET,value = "/externalApi/goods/getGoods/{barcode}", consumes = "application/json")
    GoodsDTO getGoods(@PathVariable("barcode") String barcode);
}
