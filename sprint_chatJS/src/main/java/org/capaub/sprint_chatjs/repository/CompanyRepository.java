package org.capaub.sprint_chatjs.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.capaub.sprint_chatjs.entity.Company;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {


}