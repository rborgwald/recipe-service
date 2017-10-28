package com.bufkes.service.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bufkes.service.recipe.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsernameIgnoreCase(String username);
}
