package org.capaub.msproduct.service;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.entity.Batch;
import org.capaub.msproduct.mapper.BatchMapper;
import org.capaub.msproduct.mapper.GoodsMapper;
import org.capaub.msproduct.repository.BatchRepository;
import org.capaub.msproduct.service.dto.BatchDTO;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BatchService {
    private final GoodsService goodsService;
    private final BatchMapper batchMapper;
    private final GoodsMapper goodsMapper;
    private final BatchRepository batchRepository;

    public BatchDTO createBatch(BatchDTO batchDTO, Integer companyId) {

        Batch batchDTOToBatch = batchMapper.batchDTOToBatch(batchDTO);

        GoodsDTO goodsDTO = goodsService.findGoodsByBarcode(batchDTOToBatch.getBarCode(),companyId);
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

    public List<BatchDTO> getAllBatchesByCompanyId(Integer companyId) {
        List<Batch> batches = batchRepository.findAllByGoods_CompanyId(companyId);
        return batches.stream()
                .map(batchMapper::batchToBatchDTO)
                .collect(Collectors.toList());
    }
}