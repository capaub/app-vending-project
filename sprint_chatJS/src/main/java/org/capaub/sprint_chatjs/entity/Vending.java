package org.capaub.sprint_chatjs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter @Setter @Entity @ToString
@AllArgsConstructor @NoArgsConstructor
@Table(name = "vending")
public class Vending {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private int id;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "label", length = 50)
    private String label;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "qr_code")
    private String qrCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}