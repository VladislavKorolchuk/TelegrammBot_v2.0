package com.example.telegrammbot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.telegrammbot.entity.User;
import com.example.telegrammbot.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Сущность: UserController
 *
 * @author Юрий Калынбаев
 */
@RestController
@RequestMapping("/user")
@Tag(name = "API для работы с сущностью User", description = "CRUD-операции для сущности User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получение списка всех пользователей сущности "User"
     */
    @Operation(
            summary = "Получение списка всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список пользователей",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    )
            })
    @GetMapping("/getAll")
    public Collection<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    /**
     * создание записи о новом пользователе
     */
    @Operation(
            summary = "Внесение данных о новом пользователе",
            description = "Внесение полных данных пользователя в определённом формате",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о пользователе занесена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибка со стороны сервиса(Так же при неполных данных пользователя)"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при внесении пользователя в базу из-за неверного формата данных"
                    )
            })
    @PostMapping("/create")
    public User createUserInDb(@Parameter(
            description = "Полные данные пользователя",
            example = "{firstName: Name, lastName : LastName, userPhoneNumber : +75558804420, userEmail: mail@mail.ru}")
                               @RequestBody User user) {
        return this.userService.createUserInDb(user);
    }

    /**
     * удаление данных о пользователе
     */
    @Operation(
            summary = "Удаление данных о пользователе",
            description = "Удаляет пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о пользователе удалена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Пользователь не найден в базе"
                    )
            })
    @DeleteMapping("/delete/{id}")
    public User deleteUser(@Parameter(
            description = "Id пользователя, которого необходимо удалить",
            example = "1") @PathVariable("id") Long id) {
        return this.userService.deleteUserById(id);
    }

    /**
     * изменение данных о пользователе
     */
    @Operation(
            summary = "Изменение данных о пользователе",
            description = "Ищет пользователя по id, затем заменяет в нем данные на те," +
                    " что записаны в передаваемом user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о пользователе изменена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Недостаточно данных для обновления объекта users или объект с таким id не найден"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при обновлении объекта users из-за неверного формата данных"
                    )
            })
    @PutMapping("/update/{id}")
    public User updateUser(@Parameter(
            description = "Id пользователя",
            example = "1") @PathVariable("id") Long id,
                           @Parameter(
                                   description = "Полные данные пользователя",
                                   example = "{firstName: Name, lastName : LastName," +
                                           " userPhoneNumber : +75558804420, userEmail: mail@mail.ru}")
                           @RequestBody User user) {
        return this.userService.replaceUserById(id, user);
    }
}
