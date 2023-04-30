package com.example.telegrammbot.service;

import js6team3.tbot.entity.Dog;
import js6team3.tbot.exception.DogNullParameterValueException;
import js6team3.tbot.repository.DogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса для работы с сущностью "Dog"
 *
 * @author Юрий Калынбаев
 */
@ContextConfiguration(classes = {DogService.class})
@ExtendWith(MockitoExtension.class)
class DogServiceTest {
    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private DogService dogService;

    @Test
    void testGetAllDogsFirstTest() {
        List<Dog> dogList = new ArrayList<>();
        when(dogRepository.findAll()).thenReturn(dogList);
        List<Dog> actualAllDogs = dogService.getAllDogs();
        assertSame(dogList, actualAllDogs);
        assertTrue(actualAllDogs.isEmpty());
        verify(dogRepository).findAll();
    }

    @Test
    void testGetAllDogsSecondTest() {
        when(dogRepository.findAll()).thenThrow(new DogNullParameterValueException("Message"));
        assertThrows(DogNullParameterValueException.class, () -> dogService.getAllDogs());
        verify(dogRepository).findAll();
    }

    @Test
    void testCreateDogFirst() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        when(dogRepository.save((Dog) any())).thenReturn(dog);

        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        assertSame(dog, dogService.createDogInDB(dog1));
        verify(dogRepository).save((Dog) any());
    }

    @Test
    void testDeleteCatFirst() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        when(dogRepository.findById((Long) any())).thenReturn(Optional.of(dog));
        doNothing().when(dogRepository).deleteById((Long) any());

        assertSame(dog, dogService.deleteDogById(5L));
        verify(dogRepository).findById((Long) any());
        verify(dogRepository).deleteById((Long) any());
    }

    @Test
    void testReplaceCatFirst() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        when(dogRepository.save((Dog) any())).thenReturn(dog1);
        when(dogRepository.findById((Long) any())).thenReturn(Optional.of(dog));

        assertSame(dog1, dogService.replaceDogById(5L, dog1));
        verify(dogRepository).save((Dog) any());
        verify(dogRepository).findById((Long) any());
    }

    @Test
    void testReplaceCatSecond() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        when(dogRepository.save((Dog) any())).thenThrow(new DogNullParameterValueException("Message"));
        when(dogRepository.findById((Long) any())).thenReturn(Optional.of(dog));

        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        assertThrows(DogNullParameterValueException.class, () -> dogService.replaceDogById(5L, dog1));
        verify(dogRepository).save((Dog) any());
        verify(dogRepository).findById((Long) any());
    }
}
