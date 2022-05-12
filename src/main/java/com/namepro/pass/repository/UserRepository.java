package com.namepro.pass.repository;

import com.namepro.pass.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, String> {
}
