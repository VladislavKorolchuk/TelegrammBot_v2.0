package com.example.telegrammbot.repository;

import js6team3.tbot.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности "Cat"
 *
 * @author Юрий Калынбаев
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
