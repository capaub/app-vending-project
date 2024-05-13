package org.capaub.msproduct.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter @Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date dlc;
    private int quantity;
    private Date soldOutAt;
    private String qrCode;
    @ManyToOne(fetch = FetchType.EAGER)
    private Goods goodsId;
}
