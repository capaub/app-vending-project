package org.capaub.msvending.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "spiral",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_spiral_location_vending_id",
                        columnNames = {"spiral_location", "vending_id"}
                )
        }
)
public class Spiral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String spiralLocation;
    @ManyToOne
    @JoinColumn(name = "tray_id")
    private Tray tray;
    @ManyToOne
    @JoinColumn(name = "vending_id")
    private Vending vending;
    @ManyToOne
    @JoinColumn(name = "spiral_type_id")
    private SpiralType spiralType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

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

    public Tray getTray() {
        return tray;
    }

    public void setTray(Tray tray) {
        this.tray = tray;
    }

    public Vending getVending() {
        return vending;
    }

    public void setVending(Vending vending) {
        this.vending = vending;
    }

    public SpiralType getSpiralType() {
        return spiralType;
    }

    public void setSpiralType(SpiralType spiralTypeId) {
        this.spiralType = spiralTypeId;
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
