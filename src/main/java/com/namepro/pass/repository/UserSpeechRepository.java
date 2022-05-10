package com.namepro.pass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.namepro.pass.model.UserCred;
import com.namepro.pass.model.UserSpeech;


public interface UserSpeechRepository extends JpaRepository<UserSpeech, Long> {
  List<UserSpeech> findByUsernameContaining(String username);
}
