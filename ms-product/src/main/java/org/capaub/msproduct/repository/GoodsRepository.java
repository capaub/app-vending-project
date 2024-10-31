package org.capaub.msproduct.repository;

import org.capaub.msproduct.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
    boolean existsGoodsByBarcode(String barcode);
    Optional<Goods> findGoodsByBarcode(String barcode);
}