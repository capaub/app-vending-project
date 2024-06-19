package org.capaub.sprint_chatjs.repository;

import org.capaub.sprint_chatjs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


}