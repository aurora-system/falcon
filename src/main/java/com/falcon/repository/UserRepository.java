package com.falcon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.falcon.entity.User;

@Repository
public interface UserRepository extends CrudRepository <User, Long > {
    User findByUsername(String username);
    User findByEmail(String email);
}