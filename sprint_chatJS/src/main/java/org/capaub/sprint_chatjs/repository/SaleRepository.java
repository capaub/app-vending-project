package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Integer> {


}