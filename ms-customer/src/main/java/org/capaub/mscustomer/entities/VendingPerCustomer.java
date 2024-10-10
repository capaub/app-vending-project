package org.capaub.mscustomer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendingPerCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date installationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date removalDate;
    private Integer vendingId;
    private Integer customerId;
}
