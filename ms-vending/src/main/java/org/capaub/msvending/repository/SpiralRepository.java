package org.capaub.msvending.repository;

import org.capaub.msvending.entities.Spiral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiralRepository extends JpaRepository<Spiral, Integer> {
}
