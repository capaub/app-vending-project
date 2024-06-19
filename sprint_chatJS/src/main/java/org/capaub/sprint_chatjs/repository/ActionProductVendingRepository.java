package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.ActionProductVending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ActionProductVendingRepository extends JpaRepository<ActionProductVending,Integer> {


}