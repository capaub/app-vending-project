package org.capaub.msproduct.repository;

import org.capaub.msproduct.entity.Batch;
import org.capaub.msproduct.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

    Optional<Batch> findBatchByGoodsAndDlc(Goods goods, Date dlc);

    List<Batch> findAllByGoods_CompanyIdOrderByDlcAsc(Integer companyId);

    @Query(value = """
                SELECT b.* FROM batch b
                JOIN goods g ON b.goods_id = g.id
                WHERE g.company_id = :companyId
                AND b.dlc = (SELECT MIN(b2.dlc) FROM batch b2 WHERE b2.goods_id = b.goods_id)
            """, nativeQuery = true)
    List<Batch> findShortestDlcBatchByCompany(@Param("companyId") Integer companyId);
}