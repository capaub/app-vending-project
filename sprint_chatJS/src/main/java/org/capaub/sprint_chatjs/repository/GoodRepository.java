package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<Goods,Integer> {


}