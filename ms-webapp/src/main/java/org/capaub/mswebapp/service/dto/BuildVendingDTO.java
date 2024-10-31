package org.capaub.mswebapp.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class BuildVendingDTO {
    private String vendingTags;
    private Map<String, BatchForVendingStockDTO> dataVendingStock;
    private String vendingId;
    private Integer nbVendingTray;
    private Integer nbVendingSpiral;
}