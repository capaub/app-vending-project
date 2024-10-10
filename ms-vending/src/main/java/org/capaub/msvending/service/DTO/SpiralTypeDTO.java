package org.capaub.msvending.service.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SpiralTypeDTO {
    private Integer id;
    private String label;
    private Integer maxCap;
    private Date createdAt;
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getMaxCap() {
        return maxCap;
    }

    public void setMaxCap(Integer maxCap) {
        this.maxCap = maxCap;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
