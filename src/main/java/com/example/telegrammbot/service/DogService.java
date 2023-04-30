package com.example.telegrammbot.service;

import js6team3.tbot.entity.Dog;
import js6team3.tbot.exception.DogNullParameterValueException;
import js6team3.tbot.exception.ValidationException;
import js6team3.tbot.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с сущностью "Dog"
 *
 * @author Юрий Калынбаев
 */
@Service
@RequiredArgsConstructor
public class DogService {

    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    private final DogRepository dogRepository;

    /**
     * Получение полного списка экземпляров сущности "Dog"
     *
     * @return список экземпляров сущности "Dog"
     */
    public List<Dog> getAllDogs() {
        return this.dogRepository.findAll();
    }

    /**
     * Метод сохраняет в БД созданный объект питомца.
     *
     * @param dog экземпляр сущности "Dog"
     * @throws ValidationException Calls methodValidationException(message) // @zalex14
     * @see DogRepository
     */
    public Dog createDogInDB(Dog dog) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        if (dog.getNickname() == null || dog.getNickname().isBlank() || dog.getNickname().isEmpty()) {
            throw new DogNullParameterValueException("Кличка питомца не указана");
        }
        return dogRepository.save(dog);
    }

    /**
     * Метод удаляет из БД питомца по идентификатору id
     *
     * @param id идентификатор питомца в БД
     * @see DogRepository
     */
    public Dog deleteDogById(Long id) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        Dog deleteDog = dogRepository.findById(id).orElse(null);
        dogRepository.deleteById(id);
        return deleteDog;
    }

    /**
     * Метод заменяет старые параметры питомца на те, что были переданы.
     *
     * @param id  идентификатор питомца в БД
     * @param dog экземпляр сущности "Dog"
     * @throws ValidationException Calls methodValidationException(message) // @zalex14
     * @see DogRepository
     * @see Dog
     */
    public Dog replaceDogById(Long id, Dog dog) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Текущий метод - " + methodName);
        Dog replaceDog = dogRepository.findById(id).orElse(null);
        if (replaceDog != null) {
            replaceDog.setNickname(dog.getNickname());
            replaceDog.setBreed(dog.getBreed());
            replaceDog.setAge(dog.getAge());
            replaceDog.setDescription(dog.getDescription());
        } else {
            throw new DogNullParameterValueException("Недостаточно данных при попытке заменить данные у объекта Dog");
        }
        return dogRepository.save(replaceDog);
    }
}
