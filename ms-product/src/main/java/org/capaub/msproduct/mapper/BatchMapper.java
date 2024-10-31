package org.capaub.msproduct.mapper;

import org.capaub.msproduct.entity.Batch;
import org.capaub.msproduct.service.dto.BatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BatchMapper {
    @Mapping(source = "goods.id", target = "goodsId")
    BatchDTO batchToBatchDTO(Batch batch);

    @Mapping(source = "goodsId", target = "goods.id")
    Batch batchDTOToBatch(BatchDTO batchDTO);
}