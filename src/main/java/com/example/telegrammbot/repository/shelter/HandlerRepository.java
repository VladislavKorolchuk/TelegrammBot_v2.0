package com.example.telegrammbot.repository.shelter;

import js6team3.tbot.entity.shelter.Handler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implement report access layer for recommended dog's handlers
 *
 * @author zalex14
 */
@Repository
public interface HandlerRepository extends JpaRepository<Handler, Long> {
}