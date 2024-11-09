package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.client.ProductClient;
import org.capaub.mswebapp.service.dto.BatchDTO;
import org.capaub.mswebapp.service.dto.DataForAddBatchDTO;
import org.capaub.mswebapp.service.dto.DataToChangeBatchDTO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductClient productClient;

    public List<DataForAddBatchDTO> getAllBatchAddingToVending(Integer companyId) {
        List<BatchDTO> allBatch = productClient.getAllFilteredBatch(companyId);
        List<DataForAddBatchDTO> forAddBatch = new ArrayList<>();
        allBatch.forEach(b -> {
            String barcode = b.getBarcode();
            String brand = productClient.getGoods(barcode, companyId).getBrand();

            DataForAddBatchDTO batchForForm = new DataForAddBatchDTO();

            batchForForm.setId(b.getId());
            batchForForm.setBrand(brand);

            forAddBatch.add(batchForForm);
        });
        return forAddBatch;
    }

    public DataToChangeBatchDTO getBatchToChangeInfo(Integer batchId) {
        BatchDTO batch = productClient.getBatch(batchId);
        String formatDate = new SimpleDateFormat("dd-MM-yyyy").format(batch.getUpdatedAt());
        String formatTime = new SimpleDateFormat("HH:mm:ss").format(batch.getUpdatedAt());

        DataToChangeBatchDTO batchChangeInfoDTO = new DataToChangeBatchDTO();
        batchChangeInfoDTO.setQuantity(batch.getQuantity());
        batchChangeInfoDTO.setUpdatedDateAt(formatDate);
        batchChangeInfoDTO.setUpdatedTimeAt(formatTime);

        return batchChangeInfoDTO;
    }

    public Boolean checkAvailability(Integer batchId, Integer quantity) {
        return productClient.checkAvailableQuantity(batchId, quantity);
    }

    public void decreaseQuantity(Integer batchId, Integer quantityToReduce) {
        productClient.decreaseQuantity(batchId, quantityToReduce);
    }

    public void increaseQuantity(Integer batchId, Integer quantityToAdd) {
        productClient.increaseQuantity(batchId, quantityToAdd);

    }
}