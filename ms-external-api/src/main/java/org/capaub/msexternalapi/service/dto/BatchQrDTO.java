package org.capaub.msexternalapi.service.dto;

import lombok.*;

import java.time.Instant;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
@Data
public class BatchQrDTO {
    private Integer batchId;
    private Integer companyId;
    private Instant now = Instant.now();

    public String getFilename() {
        return this.batchId + "_" + this.companyId;
    }
}
