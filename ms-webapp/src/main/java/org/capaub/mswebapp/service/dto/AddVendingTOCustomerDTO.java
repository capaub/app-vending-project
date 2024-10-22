package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddVendingTOCustomerDTO {
    private Integer vendingId;
    private Integer customerId;
    private String vendingName;
}
