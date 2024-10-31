package org.capaub.mswebapp.service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BatchForVendingStockDTO {
    private Integer id;
    private Date dlc;
    private String qrCodePath;
    private Integer quantity;
    private String barcode;
    private String brand;
    private String imgUrl;
}