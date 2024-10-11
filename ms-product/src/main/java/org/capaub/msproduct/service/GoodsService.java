package org.capaub.msproduct.service;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.entity.Goods;
import org.capaub.msproduct.mapper.GoodsMapper;
import org.capaub.msproduct.repository.GoodsRepository;
import org.capaub.msproduct.service.client.ExternalApiClient;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsMapper goodsMapper;
    private final ExternalApiClient externalApiClient;

    public GoodsDTO saveGoods(String barcode) {
        GoodsDTO goodsDTO = externalApiClient.getGoods(barcode);
        Goods goods = goodsMapper.toGoods(goodsDTO);
        return goodsMapper.toGoodsDTO(goodsRepository.save(goods));
    }

    public boolean isExist(String barcode) {
        return this.goodsRepository.existsGoodsByBarcode(barcode);
    }

    public GoodsDTO findGoodsByBarcode(String barCode) {
        Goods goods = goodsRepository.findGoodsByBarcode(barCode)
                .orElseGet(() -> goodsMapper.toGoods(saveGoods(barCode)));
        return goodsMapper.toGoodsDTO(goods);
    }

    public GoodsDTO getGoodsByBarcode(String barcode) {
        Goods goods = goodsRepository.findGoodsByBarcode(barcode).get();
        return goodsMapper.toGoodsDTO(goods);
    }

    public List<GoodsDTO> getAllGoods() {
        List<Goods> goodsList = goodsRepository.findAll();
        return goodsList.stream()
                .map(goodsMapper::toGoodsDTO)
                .collect(Collectors.toList());
    }

}
