package org.capaub.msproduct.service;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.entity.Batch;
import org.capaub.msproduct.mapper.BatchMapper;
import org.capaub.msproduct.mapper.GoodsMapper;
import org.capaub.msproduct.repository.BatchRepository;
import org.capaub.msproduct.service.dto.BatchDTO;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BatchService {
    private final GoodsService goodsService;
    private final BatchMapper batchMapper;
    private final GoodsMapper goodsMapper;
    private final BatchRepository batchRepository;

    public BatchDTO createBatch(BatchDTO batchDTO) {

        Batch batchDTOToBatch = batchMapper.batchDTOToBatch(batchDTO);

        GoodsDTO goodsDTO = goodsService.findGoodsByBarcode(batchDTOToBatch.getBarCode());
        batchDTOToBatch.setGoods(goodsMapper.toGoods(goodsDTO));

        Optional<Batch> batch = batchRepository.findBatchByGoodsAndDlc(goodsMapper.toGoods(goodsDTO), batchDTOToBatch.getDlc());

        if (batch.isPresent()) {
            Batch batchToUpdate = batch.get();
            Integer quantity = batchToUpdate.getQuantity() + batchDTOToBatch.getQuantity();
            batchToUpdate.setQuantity(quantity);
            return batchMapper.batchToBatchDTO(batchRepository.save(batchToUpdate));
        }

        return batchMapper.batchToBatchDTO(batchRepository.save(batchDTOToBatch));
    }
}