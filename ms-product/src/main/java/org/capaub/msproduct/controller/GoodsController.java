package org.capaub.msproduct.controller;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.service.GoodsService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class GoodsController {
    private final GoodsService goodsService;


}