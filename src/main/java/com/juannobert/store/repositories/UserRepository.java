package com.juannobert.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juannobert.store.models.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByEmail();
}
