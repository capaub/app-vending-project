package org.capaub.mswebapp.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoodsWithBatchesInfoDTO {
    private String brand;
    private String imgPath;
    private List<BatchInfoDTO> batches;
}