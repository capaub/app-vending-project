package org.capaub.msproduct.controller;

import org.capaub.msproduct.entities.Goods;
import org.capaub.msproduct.repository.GoodsRepository;
import org.capaub.msproduct.service.GoodsService;
import org.springframework.web.bind.annotation.*;
import pl.coderion.model.Product;


@RestController
@RequestMapping
public class GoodsController {
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping("/getGoods/{barcode}")
    public Product getApiProduct(@PathVariable String barcode) {
        Product goods = goodsService.getGood(barcode);
        return goods;
    }

    @PostMapping("/saveGoods/{barcode}")
    public Boolean saveProduct(@PathVariable String barcode) {
        Goods goods = goodsService.getGoods(barcode);
        return goodsService.saveGoods(goods);
    }
}
