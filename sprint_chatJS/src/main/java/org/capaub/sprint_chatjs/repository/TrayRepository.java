package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.Tray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrayRepository extends JpaRepository<Tray,Integer> {


}