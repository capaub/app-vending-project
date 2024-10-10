package org.capaub.mscustomer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "address_id")
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String siret;
    private String companyName;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(unique = true)
    private Integer addressId;
    private Integer companyId;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
