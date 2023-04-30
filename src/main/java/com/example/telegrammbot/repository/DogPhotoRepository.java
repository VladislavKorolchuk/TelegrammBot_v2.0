package com.example.telegrammbot.repository;

import com.example.telegrammbot.entity.DogPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности "DogPhoto"
 *
 * @author Юрий Калынбаев
 */
@Repository
public interface DogPhotoRepository extends JpaRepository<DogPhoto, Long> {
}
