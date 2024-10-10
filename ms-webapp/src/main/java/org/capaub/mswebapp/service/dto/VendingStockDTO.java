package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendingStockDTO {
    private Integer id;
    private Integer quantity;
    private Integer batchId;
    private Integer spiralId;
    private Date createdAt;
    private Date updatedAt;
}
