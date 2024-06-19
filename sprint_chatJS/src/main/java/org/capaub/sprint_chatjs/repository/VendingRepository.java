package org.capaub.sprint_chatjs.repository;

import org.apache.catalina.LifecycleState;
import org.capaub.sprint_chatjs.entity.Vending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendingRepository extends JpaRepository<Vending,Integer> {

}