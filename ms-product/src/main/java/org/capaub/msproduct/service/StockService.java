package org.capaub.msproduct.service;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.service.dto.BatchDTO;
import org.capaub.msproduct.service.dto.BatchInfoDTO;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.capaub.msproduct.service.dto.GoodsWithBatchesInfoDTO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockService {
    private GoodsService goodsService;

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

            // Créer le GoodsWithBatchesInfoDTO
            GoodsWithBatchesInfoDTO goodsWithBatches = new GoodsWithBatchesInfoDTO();
            goodsWithBatches.setBrand(goodsDTO.getBrand());
            goodsWithBatches.setImgPath(goodsDTO.getImgUrl());
            goodsWithBatches.setBatches(batchesForGoods);

            // Ajouter au stockInfo map avec le barcode comme clé
            stockInfo.put(goodsDTO.getBarcode(), goodsWithBatches);

        }
        return stockInfo;
    }

    private BatchInfoDTO createBatchInfoDTO(BatchDTO batch) {
        BatchInfoDTO batchInfo = new BatchInfoDTO();
        batchInfo.setDlc(formatDate(batch.getDlc()));
        batchInfo.setQrCodePath(batch.getQrCodePath());
        batchInfo.setQuantity(batch.getQuantity());
        batchInfo.setCreatedAt(formatDate(batch.getCreatedAt()));
        batchInfo.setUpdatedAtDate(batch.getUpdatedAt() != null ? formatDate(batch.getUpdatedAt()) : "");
        batchInfo.setUpdatedAtTime(batch.getUpdatedAt() != null ? formatTime(batch.getUpdatedAt()) : "");
        batchInfo.setSoldOutAt(batch.getSoldOutAt() != null ? formatDate(batch.getSoldOutAt()) : "");

        return batchInfo;
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    private String formatTime(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }
}