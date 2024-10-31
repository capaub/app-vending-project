package org.capaub.mswebapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockVendingSagaOrchestrator {
    private final ProductService productService;
    private final VendingService vendingService;

    public boolean executeStockVendingSaga(String location, String vendingId, Integer batchId, Integer quantity) {
        try {
            if (!productService.checkAvailability(batchId, quantity)) {
                return false;
            }

            productService.decreaseQuantity(batchId, quantity);

            vendingService.addBatchToVending(location, vendingId, batchId, quantity);

            return true;
        } catch (Exception e) {
            //TODO gestion de la compensation en cas d'echec
            return false;
        }
    }
}