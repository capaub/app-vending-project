package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Data
public class BatchDTO {
    private Integer id;
    private Date dlc;
    private Integer quantity;
    private String barcode;
    private String qrCodePath;
    private Integer goodsId;
    private Date createdAt;
    private Date updatedAt;
    private Date soldOutAt;
}