package org.capaub.sprint_chatjs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "batch")
public class Batch {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "dlc", nullable = false)
    private LocalDate dlc;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "sold_out_at")
    private Instant soldOutAt;

    @Column(name = "qr_code", nullable = false)
    private String qrCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goods_id", nullable = false)
    private Goods goods;

}