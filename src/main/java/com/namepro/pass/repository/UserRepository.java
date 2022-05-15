package com.namepro.pass.repository;

import com.namepro.pass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, String> {

    @Query("select u from User u where lower(u.firstName) like lower(concat('%', ?1,'%'))" +
            " or lower(u.lastName) like lower(concat('%', ?1,'%'))" +
            " or lower(u.fullName) like lower(concat('%', ?1,'%'))" +
            " or lower(u.lanId) like lower(concat('%', ?1, '%'))")
    List<User> findBySearchParam(String searchParam);
}
