package com.falcon.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.falcon.entity.User;

@Repository
public interface UserRepository extends CrudRepository <User, Long > {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}