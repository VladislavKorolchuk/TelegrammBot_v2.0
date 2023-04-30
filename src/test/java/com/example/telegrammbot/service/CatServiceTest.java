package com.example.telegrammbot.service;

import js6team3.tbot.entity.Cat;
import js6team3.tbot.exception.CatNullParameterValueException;
import js6team3.tbot.repository.CatRepository;
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
 * Тестирование сервиса для работы с сущностью "Cat"
 *
 * @author Юрий Калынбаев
 */
@ContextConfiguration(classes = {CatService.class})
@ExtendWith(MockitoExtension.class)
class CatServiceTest {
    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private CatService catService;

    @Test
    void testGetAllCatsFirst() {
        List<Cat> catList = new ArrayList<>();
        when(catRepository.findAll()).thenReturn(catList);
        List<Cat> actualAllCats = catService.getAllCats();
        assertSame(catList, actualAllCats);
        assertTrue(actualAllCats.isEmpty());
        verify(catRepository).findAll();
    }

    @Test
    void testGetAllCatsSecond() {
        when(catRepository.findAll()).thenThrow(new CatNullParameterValueException("Message"));
        assertThrows(CatNullParameterValueException.class, () -> catService.getAllCats());
        verify(catRepository).findAll();
    }

    @Test
    void testCreateCatFirst() {
        Cat cat = new Cat();
        cat.setId(7L);
        cat.setNickname("Артемида");
        cat.setBreed("Бенгальская кошка");
        cat.setAge(3);
        cat.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        when(catRepository.save((Cat) any())).thenReturn(cat);

        Cat cat1 = new Cat();
        cat1.setId(7L);
        cat1.setNickname("Артемида");
        cat1.setBreed("Бенгальская кошка");
        cat1.setAge(3);
        cat1.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        assertSame(cat, catService.createCatInDB(cat1));
        verify(catRepository).save((Cat) any());
    }

    @Test
    void testDeleteCatFirst() {
        Cat cat = new Cat();
        cat.setId(7L);
        cat.setNickname("Артемида");
        cat.setBreed("Бенгальская кошка");
        cat.setAge(3);
        cat.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        when(catRepository.findById((Long) any())).thenReturn(Optional.of(cat));
        doNothing().when(catRepository).deleteById((Long) any());

        assertSame(cat, catService.deleteCatById(7L));
        verify(catRepository).findById((Long) any());
        verify(catRepository).deleteById((Long) any());
    }

    @Test
    void testReplaceCatFirst() {
        Cat cat = new Cat();
        cat.setId(7L);
        cat.setNickname("Артемида");
        cat.setBreed("Бенгальская кошка");
        cat.setAge(3);
        cat.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        Cat cat1 = new Cat();
        cat1.setId(7L);
        cat1.setNickname("Артемида");
        cat1.setBreed("Бенгальская кошка");
        cat1.setAge(3);
        cat1.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        when(catRepository.save((Cat) any())).thenReturn(cat1);
        when(catRepository.findById((Long) any())).thenReturn(Optional.of(cat));

        assertSame(cat1, catService.replaceCatById(7L, cat1));
        verify(catRepository).save((Cat) any());
        verify(catRepository).findById((Long) any());
    }

    @Test
    void testReplaceCatSecond() {
        Cat cat = new Cat();
        cat.setId(7L);
        cat.setNickname("Артемида");
        cat.setBreed("Бенгальская кошка");
        cat.setAge(3);
        cat.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        when(catRepository.save((Cat) any())).thenThrow(new CatNullParameterValueException("Message"));
        when(catRepository.findById((Long) any())).thenReturn(Optional.of(cat));

        Cat cat1 = new Cat();
        cat1.setId(7L);
        cat1.setNickname("Артемида");
        cat1.setBreed("Бенгальская кошка");
        cat1.setAge(3);
        cat1.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        assertThrows(CatNullParameterValueException.class, () -> catService.replaceCatById(7L, cat1));
        verify(catRepository).save((Cat) any());
        verify(catRepository).findById((Long) any());
    }
}
