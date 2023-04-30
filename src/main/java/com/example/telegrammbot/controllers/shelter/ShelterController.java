package com.example.telegrammbot.controllers.shelter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import js6team3.tbot.entity.shelter.Shelter;
import js6team3.tbot.service.shelter.ShelterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * a REST controller for handle HTTP requests for the shelter's information
 *
 * @author zalex14
 */
@RestController
@RequestMapping("/api/shelter")
@Tag(name = "Изменить информацию о приюте", description = "CRUD-операции с приютами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ОК. Информация приюта успешно загружена/получена/обновлена"),
        @ApiResponse(responseCode = "400", description = "Ошибка 400. Параметры запроса некорректны"),
        @ApiResponse(responseCode = "404", description = "Ошибка 404. Неправильный id. Результат запроса равен NULL"),
        @ApiResponse(responseCode = "500", description = "Ошибка 500. Внутренняя ошибка программы")
})
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * Add new shelter
     *
     * @param shelter obj
     * @return create of the shelter
     */
    @Operation(summary = "Добавить новый приют",
            responses = {@ApiResponse(responseCode = "200", description = "ОК. Приют добавлен",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Shelter.class))))}, tags = "SHELTER")
    @PostMapping
    public Shelter addShelter(@RequestBody Shelter shelter) {
        return this.shelterService.addShelter(shelter);
    }

    /**
     * The shelter's remove
     *
     * @param id The shelter's id
     */
    @Operation(summary = "Удаление приюта по id",
            responses = {@ApiResponse(responseCode = "200", description = "ОК.Приют удален",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))}, tags = "SHELTER")
    @DeleteMapping("/{id}")
    public void deleteShelter(@Parameter(description = "id приюта") @PathVariable("id") Long id) {
        shelterService.deleteShelter(id);
    }

    /**
     * The shelter's update
     *
     * @param id      The shelter's id
     * @param shelter The shelter obj
     * @return The shelter obj
     */
    @Operation(summary = "Редактировать информацию о приюте",
            responses = {@ApiResponse(responseCode = "200", description = "ОК. Информация обновлена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Shelter.class))))}, tags = "SHELTER")
    @PutMapping("/{id}")
    public Shelter updateShelter(@Parameter(description = "Приют id")
                                 @PathVariable("id") Long id, @RequestBody Shelter shelter) {
        return this.shelterService.editShelter(id, shelter);
    }

    /**
     * Get a list of all shelters
     *
     * @return a list of shelters
     */
    @Operation(
            summary = "Вывести списк всех приютов",
            responses = {@ApiResponse(responseCode = "200", description = "ОК. Получен список приютов",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Shelter.class))))}, tags = "SHELTER")
    @GetMapping("/all")
    public Collection<Shelter> getAllShelters() {
        return this.shelterService.getAllShelters();
    }

    /**
     * Get the shelter's information
     *
     * @param id The shelter's id
     * @return The shelter obj
     */
    @Operation(summary = "Получить информацию о приюте по id",
            responses = {@ApiResponse(responseCode = "200", description = "Ок. Информация о приюте получена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))}, tags = "SHELTER")
    @GetMapping("/get/{id}")
    public Shelter getShelter(@Parameter(description = "id приюта") @PathVariable("id") Long id) {
        return this.shelterService.getShelter(id);
    }
}