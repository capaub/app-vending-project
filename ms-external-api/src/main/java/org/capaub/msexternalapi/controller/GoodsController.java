package org.capaub.msexternalapi.controller;

import lombok.AllArgsConstructor;
import org.capaub.msexternalapi.service.GoodsService;
import org.capaub.msexternalapi.service.dto.GoodsDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/externalApi/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/getGoods/{barcode}")
    public GoodsDTO getApiProduct(@PathVariable String barcode) {
        return goodsService.getGoods(barcode);
    }
}