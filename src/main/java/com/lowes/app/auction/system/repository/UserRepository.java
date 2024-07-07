package com.lowes.app.auction.system.repository;


import com.lowes.app.auction.system.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}