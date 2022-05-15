package com.namepro.pass.repository;

import com.namepro.pass.model.User;
import com.namepro.pass.model.UserPronunciation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPronunciationRepository extends JpaRepository<UserPronunciation, Long> {
    List<UserPronunciation> findAllByUser(User user);
}
