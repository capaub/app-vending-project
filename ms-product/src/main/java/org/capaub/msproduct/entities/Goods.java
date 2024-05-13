package org.capaub.msproduct.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String barcode;
    private String brand;
    private String img;
}
