package org.capaub.mswebapp.service.dto;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    private Integer id;
    private String barcode;
    private String brand;
    private String img;
}
