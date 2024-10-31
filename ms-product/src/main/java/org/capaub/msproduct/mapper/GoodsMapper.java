package org.capaub.msproduct.mapper;

import org.capaub.msproduct.entity.Goods;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoodsMapper {
    Goods toGoods(GoodsDTO goodsDTO);

    GoodsDTO toGoodsDTO(Goods goods);
}