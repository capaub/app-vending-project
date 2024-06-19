package org.capaub.sprint_chatjs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "spiral")
public class Spiral {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "spiral_location", nullable = false, length = 3)
    private String spiralLocation;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tray_id", nullable = false)
    private Tray tray;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spiral_type_id", nullable = false)
    private SpiralType spiralType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vending_id", nullable = false)
    private Vending vending;

}