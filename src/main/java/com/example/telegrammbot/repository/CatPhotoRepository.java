package com.example.telegrammbot.repository;

import com.example.telegrammbot.entity.CatPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности "CatPhoto"
 *
 * @author Юрий Калынбаев
 */
@Repository
public interface CatPhotoRepository extends JpaRepository<CatPhoto, Long> {
}
