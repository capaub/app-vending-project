package org.capaub.msproduct.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.capaub.msproduct.entity.Batch;
import org.capaub.msproduct.repository.BatchRepository;
import org.capaub.msproduct.service.dto.BatchDTO;
import org.capaub.msproduct.service.dto.BatchInfoDTO;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.capaub.msproduct.service.dto.GoodsWithBatchesInfoDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockService {
    private final BatchRepository batchRepository;

    public Map<String, GoodsWithBatchesInfoDTO> constructBatchInfo(List<BatchDTO> batchDTOList, List<GoodsDTO> goodsDTOList) {
        Map<String, GoodsWithBatchesInfoDTO> stockInfo = new HashMap<>();

        for (GoodsDTO goodsDTO : goodsDTOList) {
            List<BatchInfoDTO> batchesForGoods = batchDTOList.stream()
                    .filter(batch -> batch.getGoodsId().equals(goodsDTO.getId()))
                    .map(batch -> {
                        BatchInfoDTO batchInfo = new BatchInfoDTO();
                        batchInfo.setBatchId(batch.getId());
                        batchInfo.setDlc(batch.getDlc().toString());
                        batchInfo.setQuantity(batch.getQuantity());
                        batchInfo.setQrCodePath(batch.getQrCodePath());
                        batchInfo.setCreatedAt(batch.getCreatedAt().toString());
                        batchInfo.setUpdatedAtDate(batch.getUpdatedAt() != null ? batch.getUpdatedAt().toString() : "");
                        batchInfo.setUpdatedAtTime(batch.getUpdatedAt() != null ? batch.getUpdatedAt().toString() : "");
                        batchInfo.setSoldOutAt(batch.getSoldOutAt() != null ? batch.getSoldOutAt().toString() : "");
                        return batchInfo;
                    }).collect(Collectors.toList());

            GoodsWithBatchesInfoDTO goodsWithBatches = new GoodsWithBatchesInfoDTO();
            goodsWithBatches.setBrand(goodsDTO.getBrand());
            goodsWithBatches.setImgPath(goodsDTO.getImgUrl());
            goodsWithBatches.setBatches(batchesForGoods);

            stockInfo.put(goodsDTO.getBarcode(), goodsWithBatches);

        }
        return stockInfo;
    }

    public Boolean checkAvailableQuantity(Integer batchId, Integer quantityToCheck) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new EntityNotFoundException("Batch not found with ID: " + batchId));

        Integer quantity = batch.getQuantity();
        return quantity >= quantityToCheck;
    }

    public void decreaseQuantity(Integer batchId, Integer quantityToReduce) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new EntityNotFoundException("Batch not found with ID: " + batchId));
        batch.setQuantity(batch.getQuantity() - quantityToReduce);
        batchRepository.save(batch);
    }

    public void increaseQuantity(Integer batchId, Integer quantityToAdd) {
        Batch batch= batchRepository.findById(batchId)
                .orElseThrow(() -> new EntityNotFoundException("Batch not found with ID: " + batchId));
        batch.setQuantity(batch.getQuantity() + quantityToAdd);
        batchRepository.save(batch);
    }
}