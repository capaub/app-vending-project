package org.capaub.msexternalapi.service.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    private Integer id;
    private String barcode;
    private String brand;
    private String imgUrl;
    private Date createdAt;
    private Date updatedAt;
}
