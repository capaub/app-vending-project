package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Integer> {


}