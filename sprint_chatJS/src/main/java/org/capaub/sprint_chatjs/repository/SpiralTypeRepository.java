package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.SpiralType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiralTypeRepository extends JpaRepository<SpiralType,Integer> {


}