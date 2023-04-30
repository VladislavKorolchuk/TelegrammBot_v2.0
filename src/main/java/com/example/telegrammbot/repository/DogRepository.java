package com.example.telegrammbot.repository;

import js6team3.tbot.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности "Dog"
 *
 * @author Юрий Калынбаев
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
