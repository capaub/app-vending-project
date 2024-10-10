package org.capaub.msvending.repository;

import org.capaub.msvending.entities.SpiralType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiralTypeRepository extends JpaRepository<SpiralType, Integer> {
}
