package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.obj.user.User;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);

}
