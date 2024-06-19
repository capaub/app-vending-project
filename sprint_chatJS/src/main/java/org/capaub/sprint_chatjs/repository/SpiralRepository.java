package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.Spiral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiralRepository extends JpaRepository<Spiral,Integer> {


}