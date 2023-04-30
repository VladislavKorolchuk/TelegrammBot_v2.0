package com.example.telegrammbot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import js6team3.tbot.entity.Dog;
import js6team3.tbot.entity.DogPhoto;
import js6team3.tbot.service.DogPhotoService;
import js6team3.tbot.service.DogService;
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
 * Сущность: DogController
 *
 * @author Юрий Калынбаев
 */
@RestController
@RequestMapping("/dogs")
@Tag(name = "API для работы с сущностью Dog", description = "CRUD-операции для сущности Dog")
public class DogController {

    /**
     * поле "SIZE_PHOTO" - максимальный размер файла
     */
    @Value("${size.PhotoDog}")
    int SIZE_PHOTO;

    private final DogService dogService;
    private final DogPhotoService dogPhotoService;

    public DogController(DogService dogService, DogPhotoService dogPhotoService) {
        this.dogService = dogService;
        this.dogPhotoService = dogPhotoService;
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
                            array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                    ))})
    @PostMapping("/create")
    public Dog createDog(@RequestBody Dog dog) {
        return this.dogService.createDogInDB(dog);
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
                            array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                    ))})
    @PutMapping("/update/{id}")
    public Dog updateDog(@Parameter(description = "Id питомца", example = "1") @PathVariable("id") Long id,
                         @RequestBody Dog dog) {
        return this.dogService.replaceDogById(id, dog);
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
    public Dog deleteDog(@Parameter(description = "Id питомца", example = "1") @PathVariable("id") Long id) {
        return this.dogService.deleteDogById(id);
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

    @PostMapping(value = "/{id}/load/PhotoDog", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    /**
     * Прикрепление фото питомца
     */
    public ResponseEntity<String> uploadPhotoDog(@Parameter(description = "Id питомца", example = "1")
                                                 @PathVariable Long id, @Parameter(description = "Путь к фотографии")
                                                 @RequestParam MultipartFile photoDog) throws
            IOException {
        /**
         * ограничение размера файла
         */
        if (photoDog.getSize() >= SIZE_PHOTO) {         // Ограничение размера файла
            return ResponseEntity.badRequest().body("Файл большого размера");
        }
        dogPhotoService.uploadPhotoDog(id, photoDog);
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
    @GetMapping(value = "/{id}/fotoDog")
    public void downloadPhotoDog(@Parameter(description = "Id питомца", example = "1") @PathVariable Long id,
                                 HttpServletResponse response) throws IOException {
        DogPhoto dogPhoto = dogPhotoService.findPhotoDog(id);
        Path path = Path.of(dogPhoto.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(dogPhoto.getMediaType());
            response.setContentLength((int) dogPhoto.getFileSize());
            is.transferTo(os);
        }
    }
}
