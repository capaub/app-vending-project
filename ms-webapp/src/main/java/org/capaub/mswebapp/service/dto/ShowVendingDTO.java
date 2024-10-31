package org.capaub.mswebapp.service.dto;

import lombok.Data;

@Data
public class ShowVendingDTO {
    private String vendingTags;
    private String dataVendingStock;
    private String vendingId;
    private Integer nbVendingTray;
    private Integer nbVendingSpiral;
}