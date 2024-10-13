package org.capaub.mswebapp.service.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsWithBatchesInfoDTO {
    private String brand;
    private String imgPath;
    private List<BatchInfoDTO> batches;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<BatchInfoDTO> getBatches() {
        return batches;
    }

    public void setBatches(List<BatchInfoDTO> batches) {
        this.batches = batches;
    }
}
