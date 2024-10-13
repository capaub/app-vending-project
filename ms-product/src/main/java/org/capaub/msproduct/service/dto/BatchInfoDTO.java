package org.capaub.msproduct.service.dto;

import org.springframework.stereotype.Component;

@Component
public class BatchInfoDTO {
    private Integer batchId;
    private String dlc;
    private String qrCodePath;
    private Integer quantity;
    private String createdAt;
    private String updatedAtDate;
    private String updatedAtTime;
    private String soldOutAt;

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getDlc() {
        return dlc;
    }

    public void setDlc(String dlc) {
        this.dlc = dlc;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAtDate() {
        return updatedAtDate;
    }

    public void setUpdatedAtDate(String updatedAtDate) {
        this.updatedAtDate = updatedAtDate;
    }

    public String getUpdatedAtTime() {
        return updatedAtTime;
    }

    public void setUpdatedAtTime(String updatedAtTime) {
        this.updatedAtTime = updatedAtTime;
    }

    public String getSoldOutAt() {
        return soldOutAt;
    }

    public void setSoldOutAt(String soldOutAt) {
        this.soldOutAt = soldOutAt;
    }
}