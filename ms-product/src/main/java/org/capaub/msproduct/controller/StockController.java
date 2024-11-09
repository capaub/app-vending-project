package org.capaub.msproduct.controller;

import lombok.AllArgsConstructor;
import org.capaub.msproduct.service.BatchService;
import org.capaub.msproduct.service.GoodsService;
import org.capaub.msproduct.service.StockService;
import org.capaub.msproduct.service.dto.BatchDTO;
import org.capaub.msproduct.service.dto.GoodsDTO;
import org.capaub.msproduct.service.dto.GoodsWithBatchesInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class StockController {
    private BatchService batchService;
    private GoodsService goodsService;
    private StockService stockService;

    @GetMapping("/batchesInfo/{companyId}")
    public ResponseEntity<Map<String, GoodsWithBatchesInfoDTO>> getStockInfo(@PathVariable Integer companyId) {
        List<BatchDTO> batchesDTO = batchService.getAllBatchesByCompanyId(companyId);
        List<GoodsDTO> goodsDTOList = goodsService.getAllGoods();
        Map<String, GoodsWithBatchesInfoDTO> stockInfo = stockService.constructBatchInfo(batchesDTO,goodsDTOList);
        return ResponseEntity.ok(stockInfo);
    }

    @GetMapping("/checkAvailableQuantity/{batchId}/{quantity}")
    public Boolean checkAvailableQuantity(@PathVariable Integer batchId, @PathVariable Integer quantity) {
        return stockService.checkAvailableQuantity(batchId,quantity);
    }

    @PostMapping("/decreaseQuantity/{batchId}/{quantityToReduce}")
    public void decreaseQuantity(@PathVariable Integer batchId, @PathVariable Integer quantityToReduce) {
        stockService.decreaseQuantity(batchId,quantityToReduce);
    }

    @PostMapping("/increaseQuantity/{batchId}/{quantityToAdd}")
    public void increaseQuantity(@PathVariable Integer batchId, @PathVariable Integer quantityToAdd) {
        stockService.increaseQuantity(batchId,quantityToAdd);
    }
}