package com.namepro.pass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.namepro.pass.model.UserCred;


public interface UserCredRepository extends JpaRepository<UserCred, Long> {
  List<UserCred> findByUsernameContaining(String username);
}
