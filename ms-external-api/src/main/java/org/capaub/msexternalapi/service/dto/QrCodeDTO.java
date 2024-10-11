package org.capaub.msexternalapi.service.dto;

import lombok.*;

import java.time.Instant;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
@Data
public class QrCodeDTO {

    private Integer batchId;
    private String filename;
    private Instant now = Instant.now();

}
