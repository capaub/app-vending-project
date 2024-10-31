package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Data
public class GoodsDTO {
    private Integer id;
    private String barcode;
    private String brand;
    private String imgUrl;
    private Date createdAt;
    private Date updatedAt;
}