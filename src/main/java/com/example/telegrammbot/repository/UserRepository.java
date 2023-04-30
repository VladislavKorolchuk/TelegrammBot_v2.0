package com.example.telegrammbot.repository;

import js6team3.tbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности "User"
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
