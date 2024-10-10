package org.capaub.msvending.service.DTO;

import lombok.*;

import java.util.Date;

@Data
public class BatchDTO {
    private Integer id;
    private Date dlc;
    private Integer quantity;
    private Date soldOutAt;
    private String qrCode;
    private Integer goodsId;
}