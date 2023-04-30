package com.example.telegrammbot.repository;

import com.example.telegrammbot.entity.Cat;
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
