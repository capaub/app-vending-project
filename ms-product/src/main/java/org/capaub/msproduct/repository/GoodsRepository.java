package org.capaub.msproduct.repository;

import org.capaub.msproduct.entities.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
    boolean existsGoodsByBarcode(String barcode);
}
