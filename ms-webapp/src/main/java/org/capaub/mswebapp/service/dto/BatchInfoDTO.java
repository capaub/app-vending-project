package org.capaub.mswebapp.service.dto;

import lombok.*;

@Data
public class BatchInfoDTO {
    private String dlc;
    private Integer batchId;
    private String qrCodePath;
    private Integer quantity;
    private String createdAt;
    private String updatedAtDate;
    private String updatedAtTime;
    private String soldOutAt;
}