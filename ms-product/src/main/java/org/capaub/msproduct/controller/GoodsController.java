package org.capaub.msproduct.controller;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.service.GoodsService;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/goods")
public class GoodsController {
    private final GoodsService goodsService;

    @PostMapping("/create/{barcode}/{companyId}")
    public GoodsDTO createGoods(@PathVariable String barcode, @PathVariable Integer companyId) {
        return goodsService.saveGoods(barcode, companyId);
    }

    @GetMapping("/findByBarcode/{barcode}/{companyId}")
    public GoodsDTO getGoods(@PathVariable String barcode, @PathVariable Integer companyId) {
        return goodsService.findGoodsByBarcode(barcode,companyId);
    }

}