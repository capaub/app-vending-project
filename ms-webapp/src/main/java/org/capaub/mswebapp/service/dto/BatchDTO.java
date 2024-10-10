package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {
    private Integer id;
    private Date dlc;
    private Integer quantity;
    private Date soldOutAt;
    private String qrCode;
    private Integer goodsId;
}
