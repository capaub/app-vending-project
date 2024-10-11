package org.capaub.msproduct.repository;

import org.capaub.msproduct.entity.Batch;
import org.capaub.msproduct.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

    Optional<Batch> findBatchByGoodsAndDlc(Goods goods,Date dlc);
}
