package com.example.telegrammbot.service;

import js6team3.tbot.entity.User;
import js6team3.tbot.exception.UsersNullParameterValueException;
import js6team3.tbot.exception.ValidationException;
import js6team3.tbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Сервис для работы с сущностью "User"
 *
 * @author Юрий Калынбаев
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    /**
     * Получение полного списка экземпляров сущности "Cat"
     *
     * @return список экземпляров сущности "Cat"
     */
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Метод сохраняет в БД созданный объект пользователя
     *
     * @param user передает пользователя
     * @see UserRepository
     * @see User
     */
    public User createUserInDb(User user) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        String userNewNumber = user.getUserPhoneNumber();
        if (user.getFirstName() == null || user.getFirstName().isBlank() || user.getFirstName().isBlank()) {
            throw new UsersNullParameterValueException("Имя пользователя не указано");
        }
        if (user.getLastName() == null || user.getLastName().isBlank() || user.getLastName().isBlank()) {
            throw new UsersNullParameterValueException("Фамилия пользователя не указана");
        }
        // Форматирование телефона пользователя, если телефон указан неверно получаем null
        if (userNewNumber != null && MatchingPhoneNumber(userNewNumber) != null) {
            user.setUserPhoneNumber(MatchingPhoneNumber(userNewNumber));
        } else {
            throw new UsersNullParameterValueException("Телефон пользователя не указан или не соответствует формату");
        }
        if (!ValidityEmail(user.getUserEmail())) {
            throw new UsersNullParameterValueException("Почта пользователя не указана или не соответствует формату");
        }
        return userRepository.save(user);
    }

    /**
     * Метод удаляет из БД пользователя по идентификатору id
     *
     * @param id идентификатор пользователя в БД
     * @see UserRepository
     */
    public User deleteUserById(Long id) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        User deleteUser = userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
        return deleteUser;
    }

    /**
     * Метод заменяет старые параметры пользователя на те, что были переданы.
     *
     * @param id   идентификатор пользователя в БД
     * @param user экземпляр сущности "User"
     * @throws ValidationException Calls methodValidationException(message) // @zalex14
     * @see UserRepository
     * @see User
     */
    public User replaceUserById(Long id, User user) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        User replaceUser = userRepository.findById(id).orElse(null);
        if (replaceUser != null) {
            replaceUser.setFirstName(user.getFirstName());
            replaceUser.setLastName(user.getLastName());
            replaceUser.setUserPhoneNumber(user.getUserPhoneNumber());
            replaceUser.setUserEmail(user.getUserEmail());
        } else {
            throw new UsersNullParameterValueException("Недостаточно данных при попытке" +
                    " заменить данные у объекта users");
        }
        return userRepository.save(replaceUser);
    }

    /**
     * Метод проверяет правильность написания номера телефона, если номер указан неверно, метод возвращает null
     * Если номер указан верно, он подгоняется под общий формат
     *
     * @param telephone номер телефона пользователя
     * @see UserRepository
     */
    public String MatchingPhoneNumber(String telephone) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        if (telephone.chars().filter(Character::isDigit).count() == 11) {
            String str = telephone.replaceAll("\\D+", "");
            String firstCharacter = str.substring(0, 1);
            if (firstCharacter.equals("8")) {
                return (str.charAt(0) + "(" + str.substring(1, 4) + ")" + str.substring(4, 7)
                        + "-" + str.substring(7, 9) + "-" + str.substring(9, 11));
            } else {
                return ("+" + str.charAt(0) + "(" + str.substring(1, 4) + ")" + str.substring(4, 7)
                        + "-" + str.substring(7, 9) + "-" + str.substring(9, 11));
            }
        } else {
            return null;
        }
    }

    /**
     * Метод проверяет правильность написания электронной почты пользователя,
     * если почта указана неверно, метод возвращает false
     *
     * @param eMail электронная почта пользователя
     * @see UserRepository
     */
    public boolean ValidityEmail(String eMail) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        if (eMail == null) {
            return false;
        } else {
            String regexPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
            return patternMatches(eMail, regexPattern);
        }
    }

    /**
     * Вспомогательный метод, соответствующий шаблону регулярных выражений
     *
     * @param TheStringBeingChecked строка которую нужно проверить
     * @param regexPattern          паттерн для проверки строки
     * @see Pattern
     * @see java.util.regex.Matcher
     */
    public static boolean patternMatches(String TheStringBeingChecked, String regexPattern) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);

        return Pattern.compile(regexPattern)
                .matcher(TheStringBeingChecked)
                .matches();
    }

}
