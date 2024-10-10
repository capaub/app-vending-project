package org.capaub.msvending.service.DTO;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SpiralDTO {
    private Integer id;
    private String spiralLocation;
    private Integer trayId;
    private Integer vendingId;
    private Integer spiralTypeId;
    private Date createdAt;
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpiralLocation() {
        return spiralLocation;
    }

    public void setSpiralLocation(String spiralLocation) {
        this.spiralLocation = spiralLocation;
    }

    public Integer getTrayId() {
        return trayId;
    }

    public void setTrayId(Integer trayId) {
        this.trayId = trayId;
    }

    public Integer getVendingId() {
        return vendingId;
    }

    public void setVendingId(Integer vendingId) {
        this.vendingId = vendingId;
    }

    public Integer getSpiralTypeId() {
        return spiralTypeId;
    }

    public void setSpiralTypeId(Integer spiralTypeId) {
        this.spiralTypeId = spiralTypeId;
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
