package com.example.telegrammbot.service;

import js6team3.tbot.entity.Cat;
import js6team3.tbot.entity.CatPhoto;
import js6team3.tbot.repository.CatPhotoRepository;
import js6team3.tbot.repository.CatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис для работы с сущностью "CatPhoto"
 *
 * @author Юрий Калынбаев
 */
@Service
@RequiredArgsConstructor
public class CatPhotoService {

    private static final Logger logger = LoggerFactory.getLogger(CatPhotoService.class);

    private final CatRepository catRepository;
    private final CatPhotoRepository catPhotoRepository;

    @Value("${dir.path.PhotoCat}")
    /**
     * "PhotoCatDir" - директория фото сущности Cat
     */
    private String photoCatDir;

    /**
     * Метод загружает фото питомца в базу данных и на диск
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * Используется метод репозитория {@link CatPhotoService#getExtensions(String)}
     *
     * @param id   идентификатор питомца
     * @param file считываемый файл
     * @throws IOException - может возникнуть исключение ввода/вывода
     */
    public CatPhoto uploadPhotoCat(Long id, MultipartFile file) throws IOException {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        Cat cat = catRepository.findById(id).orElseThrow();
        Path filePath = Path.of(photoCatDir, id + "." + getExtensions(file.getOriginalFilename()));
        /** создаем директорию, если её нет */
        Files.createDirectories(filePath.getParent());
        /**
         * если такой файл существует, то мы его удаляем
         */
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 2048);
                BufferedOutputStream bos = new BufferedOutputStream(os, 2048)
        ) {
            bis.transferTo(bos);
        }
        CatPhoto catPhoto = catPhotoRepository.findById(id).orElseGet(CatPhoto::new);
        catPhoto.setCat(cat);
        catPhoto.setFilePath(filePath.toString());
        catPhoto.setFileSize(file.getSize());
        catPhoto.setMediaType(file.getContentType());
        catPhoto.setPhotoCat(file.getBytes());
        return catPhotoRepository.save(catPhoto);
    }

    /**
     * Метод формирует имя файла
     *
     * @return Возвращает имя файла
     */
    public String getExtensions(String fileName) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Метод ищет питомца по его id идентификатору
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param catId идентификатор питомца
     * @return Возвращает найденного питомца
     */
    public Cat findCatById(Long catId) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        return catRepository.findById(catId).orElseThrow();
    }

    /**
     * Метод ищет фото питомца по id идентификатору питомца
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param catId идентификатор питомца
     * @return Возвращает объект фото
     * @throws IOException - может возникнуть исключение ввода/вывода
     */
    public CatPhoto findPhotoCat(Long catId) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        return catPhotoRepository.findById(catId).orElseThrow();
    }
}