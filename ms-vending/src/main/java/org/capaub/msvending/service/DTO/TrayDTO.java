package org.capaub.msvending.service.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TrayDTO {
    private Integer id;
    private Integer vendingId;
    private Date createdAt;
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVendingId() {
        return vendingId;
    }

    public void setVendingId(Integer vendingId) {
        this.vendingId = vendingId;
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
