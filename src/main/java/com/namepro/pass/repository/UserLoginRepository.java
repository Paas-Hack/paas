package com.namepro.pass.repository;

import com.namepro.pass.model.User;
import com.namepro.pass.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    UserLogin findByUser(User user);
}
