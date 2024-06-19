package org.capaub.sprint_chatjs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "vending_per_customer")
public class VendingPerCustomer {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "install_date", nullable = false)
    private Instant installDate;

    @Column(name = "removal_date")
    private Instant removalDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vending_id", nullable = false)
    private Vending vending;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}