package com.peachkoder.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.peachkoder.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);

}
