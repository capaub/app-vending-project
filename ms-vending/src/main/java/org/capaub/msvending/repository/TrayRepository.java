package org.capaub.msvending.repository;

import org.capaub.msvending.entities.Tray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrayRepository extends JpaRepository<Tray,Integer> {
}
