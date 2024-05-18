package org.capaub.msproduct.service;

import org.capaub.msproduct.entities.Goods;
import org.capaub.msproduct.repository.GoodsRepository;
import org.springframework.stereotype.Service;
import pl.coderion.model.Product;

@Service
public class GoodsService {

    private final OpenFoodFactApiService offService;
    private final GoodsRepository goodsRepository;


    public GoodsService(OpenFoodFactApiService offService, GoodsRepository goodsRepository) {
        this.offService = offService;
        this.goodsRepository = goodsRepository;
    }

    public Goods getGoods(String barcode) {
        Product product = getProduct(barcode);
        Goods goods = new Goods();
        goods.setBarcode(product.getCode());
        goods.setBrand(product.getBrands());
        goods.setImg(product.getImageSmallUrl());
        return goods;
    }
    public Product getProduct(String barcode) {
        return this.offService.getProductDetails(barcode);
    }
    public boolean saveGoods(Goods goods) {
        if (!this.goodsRepository.existsGoodsByBarcode(goods.getBarcode())){
            this.goodsRepository.save(goods);
            return true;
        } return false;
    }

}
