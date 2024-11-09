package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockVendingSagaOrchestrator {
    private final ProductService productService;
    private final VendingService vendingService;

    public boolean executeStockVendingSaga(String location, String vendingId, Integer batchId, Integer quantity) {
        boolean productChecked = false;
        boolean quantityDecreased = false;
        boolean batchAdded = false;

        try {
            productChecked = productService.checkAvailability(batchId, quantity);
            if (!productChecked) {
                return false;
            }

            productService.decreaseQuantity(batchId, quantity);
            quantityDecreased = true;

            vendingService.addBatchToVending(location, vendingId, batchId, quantity);
            batchAdded = true;

            return true;
        } catch (Exception e) {
            productService.increaseQuantity(batchId, quantity);
            vendingService.cancelLastUpdateSpiralStock(vendingId);
            return false;
        }
    }
}