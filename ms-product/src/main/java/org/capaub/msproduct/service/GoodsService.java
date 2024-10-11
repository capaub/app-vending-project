package org.capaub.msproduct.service;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.entities.Goods;
import org.capaub.msproduct.repository.GoodsRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public boolean saveGoods(Goods goods) {
        if (!this.goodsRepository.existsGoodsByBarcode(goods.getBarcode())){
            this.goodsRepository.save(goods);
            return true;
        } return false;
    }

}
