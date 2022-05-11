package com.namepro.pass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.namepro.pass.model.UserCred;
import org.springframework.data.repository.CrudRepository;


public interface UserCredRepository extends CrudRepository<UserCred, Long> {
  List<UserCred> findByUsernameContaining(String username);

  List<UserCred> findByUsername(String username);
}
