package com.example.telegrammbot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import js6team3.tbot.entity.Cat;
import js6team3.tbot.entity.CatPhoto;
import js6team3.tbot.service.CatPhotoService;
import js6team3.tbot.service.CatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Сущность: CatController
 *
 * @author Юрий Калынбаев
 */
@RestController
@RequestMapping("/cats")
@Tag(name = "API для работы с сущностью Cat", description = "CRUD-операции для сущности Cat")
public class CatController {

    /**
     * поле "SIZE_PHOTO" - максимальный размер файла
     */
    @Value("${size.PhotoDog}")
    int SIZE_PHOTO;

    private final CatService catService;
    private final CatPhotoService catPhotoService;

    public CatController(CatService catService, CatPhotoService catPhotoService) {
        this.catService = catService;
        this.catPhotoService = catPhotoService;
    }

    /**
     * создание новой записи о питомце
     */
    @Operation(summary = "Внесение данных о новом питомце\"",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о питомце занесена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                    ))})
    @PostMapping("/create")
    public Cat createCat(@RequestBody Cat cat) {
        return this.catService.createCatInDB(cat);
    }

    /**
     * изменение данных о питомце
     */
    @Operation(summary = "Изменение данных о питомце",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о питомце изменена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Cat.class))
                    ))})
    @PutMapping("/update/{id}")
    public Cat updateCat(@Parameter(description = "Id питомца", example = "1") @PathVariable("id") Long id,
                         @RequestBody Cat cat) {
        return this.catService.replaceCatById(id, cat);
    }

    /**
     * удаление данных о питомце
     */
    @Operation(summary = "Удаление информации о питомце",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о питомце удалена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ))})
    @DeleteMapping("/delete/{id}")
    public Cat deleteCat(@Parameter(description = "Id питомца", example = "1") @PathVariable("id") Long id) {
        return this.catService.deleteCatById(id);
    }

    /**
     * загрузка фото питомца
     */
    @Operation(summary = "Загрузка фотографии питомца",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Фотография загружена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ))})

    @PostMapping(value = "/{id}/load/PhotoCat", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    /**
     * Прикрепление фото питомца
     */
    public ResponseEntity<String> uploadPhotoCat(@Parameter(description = "Id питомца", example = "1")
                                                 @PathVariable Long id, @Parameter(description = "Путь к фотографии")
                                                 @RequestParam MultipartFile photoCat) throws
            IOException {
        /**
         * ограничение размера файла
         */
        if (photoCat.getSize() >= SIZE_PHOTO) {         // Ограничение размера файла
            return ResponseEntity.badRequest().body("Файл большого размера");
        }
        catPhotoService.uploadPhotoCat(id, photoCat);
        return ResponseEntity.ok().build();
    }

    /**
     * просмотр фото питомца
     */
    @Operation(summary = "Просмотр фотографии питомца",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Фотография найдена",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ))})
    @GetMapping(value = "/{id}/fotoCat")
    public void downloadPhotoCat(@Parameter(description = "Id питомца", example = "1") @PathVariable Long id,
                                 HttpServletResponse response) throws IOException {
        CatPhoto catPhoto = catPhotoService.findPhotoCat(id);
        Path path = Path.of(catPhoto.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(catPhoto.getMediaType());
            response.setContentLength((int) catPhoto.getFileSize());
            is.transferTo(os);
        }
    }
}
