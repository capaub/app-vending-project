package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendingPerCustomerDTO {
    private Integer id;
    private Date installationDate;
    private Date removalDate;
    private Integer vendingId;
    private Integer customerId;
}
