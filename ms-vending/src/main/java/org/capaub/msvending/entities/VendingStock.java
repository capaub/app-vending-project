package org.capaub.msvending.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendingStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    @ElementCollection
    private List<Integer> batchIds;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vending_stock_spiral",
            joinColumns = @JoinColumn(name = "vending_stock_id"),
            inverseJoinColumns = @JoinColumn(name = "spiral_id")
    )
    private List<Spiral> spiralId;
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
}
