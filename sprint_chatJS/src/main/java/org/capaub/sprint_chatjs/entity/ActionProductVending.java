package org.capaub.sprint_chatjs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "action_product_vending")
public class ActionProductVending {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "added_product", nullable = false)
    private Boolean addedProduct = false;

    @Column(name = "removed_product", nullable = false)
    private Boolean removedProduct = false;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spiral_id", nullable = false)
    private Spiral spiral;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vending_stock_id", nullable = false)
    private VendingStock vendingStock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}