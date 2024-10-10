package org.capaub.msvending.repository;

import org.capaub.msvending.entities.Vending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendingRepository extends JpaRepository<Vending, Integer> {
}
