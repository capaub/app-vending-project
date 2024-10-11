package org.capaub.msexternalapi.service;

import lombok.AllArgsConstructor;
import org.capaub.msexternalapi.service.dto.GoodsDTO;
import org.springframework.stereotype.Service;
import pl.coderion.model.Product;

@Service
@AllArgsConstructor
public class GoodsService {

    private final OpenFoodFactApiService offService;

    public GoodsDTO getGoods(String barcode) {
        Product product = getProduct(barcode);
        GoodsDTO goods = new GoodsDTO();
        goods.setBarcode(product.getCode());
        goods.setBrand(product.getBrands());
        goods.setImg(product.getImageSmallUrl());
        return goods;
    }

    public Product getProduct(String barcode) {
        return offService.getProductDetails(barcode);
    }
}