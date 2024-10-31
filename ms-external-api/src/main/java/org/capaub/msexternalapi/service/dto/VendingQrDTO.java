package org.capaub.msexternalapi.service.dto;

import lombok.*;
import java.time.Instant;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendingQrDTO {
    private Integer vendingId;
    private Integer customerId;
    private Integer companyId;
    private Instant now = Instant.now();

    public String getFilename() {
        return this.vendingId + "_" + this.customerId + "_" + this.companyId;
    }
}