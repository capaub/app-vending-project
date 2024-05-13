package org.capaub.msproduct.repository;

import org.capaub.msproduct.entities.Batch;
import org.capaub.msproduct.entities.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

    boolean existsBatchByGoodsIdAndDlc(Goods goodsId, Date dlc);
}
