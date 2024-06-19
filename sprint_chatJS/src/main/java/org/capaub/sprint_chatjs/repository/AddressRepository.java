package org.capaub.sprint_chatjs.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.capaub.sprint_chatjs.entity.Address;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {


}