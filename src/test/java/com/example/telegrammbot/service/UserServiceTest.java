package com.example.telegrammbot.service;

import js6team3.tbot.entity.Dog;
import js6team3.tbot.entity.User;
import js6team3.tbot.exception.UsersNullParameterValueException;
import js6team3.tbot.repository.UserRepository;
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
 * Тестирование сервиса для работы с сущностью "User"
 *
 * @author Юрий Калынбаев
 */
@ContextConfiguration(classes = {UserService.class})
@ExtendWith(MockitoExtension.class)
class UsersServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsersFirst() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUsers = userService.getAllUsers();
        assertSame(userList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void testGetAllUsersSecond() {
        when(userRepository.findAll()).thenThrow(new UsersNullParameterValueException("Message"));
        assertThrows(UsersNullParameterValueException.class, () -> userService.getAllUsers());
        verify(userRepository).findAll();
    }


    @Test
    void testCreateUserFirst() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user = new User();
        user.setDog(dog);
        user.setFirstName("Иван");
        user.setId(5L);
        user.setLastName("Иванов");
        user.setUserEmail("ivan.ivanov@example.org");
        user.setUserPhoneNumber("5555555555");

        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user1 = mock(User.class);

        when(user1.getUserPhoneNumber()).thenReturn("5555555555");
        when(user1.getFirstName()).thenReturn("Иван");
        when(user1.getLastName()).thenReturn("Иванов");

        doNothing().when(user1).setDog((Dog) any());
        doNothing().when(user1).setFirstName((String) any());
        doNothing().when(user1).setId((Long) any());
        doNothing().when(user1).setLastName((String) any());
        doNothing().when(user1).setUserEmail((String) any());
        doNothing().when(user1).setUserPhoneNumber((String) any());

        user1.setDog(dog);
        user1.setFirstName("Иван");
        user1.setId(5L);
        user1.setLastName("Иванов");
        user1.setUserEmail("ivan.ivanov@example.org");
        user1.setUserPhoneNumber("5555555555");

        assertThrows(UsersNullParameterValueException.class, () -> userService.createUserInDb(user1));

        verify(user1, atLeast(1)).getFirstName();
        verify(user1, atLeast(1)).getUserPhoneNumber();
        verify(user1).setDog((Dog) any());
        verify(user1).setFirstName((String) any());
        verify(user1).setId((Long) any());
        verify(user1).setLastName((String) any());
        verify(user1).setUserEmail((String) any());
        verify(user1, atLeast(1)).setUserPhoneNumber((String) any());
    }

    @Test
    void testCreateUserSecond() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user = new User();
        user.setDog(dog);
        user.setFirstName("Иван");
        user.setId(5L);
        user.setLastName("Иванов");
        user.setUserEmail("ivan.ivanov@example.org");
        user.setUserPhoneNumber("5555555555");

        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user1 = mock(User.class);

        when(user1.getUserPhoneNumber()).thenReturn("5555555555");
        when(user1.getFirstName()).thenReturn("Иван");
        doNothing().when(user1).setDog((Dog) any());
        doNothing().when(user1).setFirstName((String) any());
        doNothing().when(user1).setId((Long) any());
        doNothing().when(user1).setLastName((String) any());
        doNothing().when(user1).setUserEmail((String) any());
        doNothing().when(user1).setUserPhoneNumber((String) any());

        user1.setDog(dog1);
        user1.setFirstName("Иван");
        user1.setId(5L);
        user1.setLastName("Иванов");
        user1.setUserEmail("ivan.ivanov@example.org");
        user1.setUserPhoneNumber("5555555555");

        assertThrows(UsersNullParameterValueException.class, () -> userService.createUserInDb(user1));
        verify(user1, atLeast(1)).getFirstName();
        verify(user1, atLeast(1)).getUserPhoneNumber();
        verify(user1).setDog((Dog) any());
        verify(user1).setFirstName((String) any());
        verify(user1).setId((Long) any());
        verify(user1).setLastName((String) any());
        verify(user1).setUserEmail((String) any());
        verify(user1, atLeast(1)).setUserPhoneNumber((String) any());
    }


    @Test
    void testCreateUserThird() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user = new User();
        user.setDog(dog);
        user.setFirstName("Иван");
        user.setId(5L);
        user.setLastName("Иванов");
        user.setUserEmail("ivan.ivanov@example.org");
        user.setUserPhoneNumber("5555555555");

        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user1 = mock(User.class);

        when(user1.getUserPhoneNumber()).thenReturn("5555555555");
        when(user1.getFirstName()).thenReturn("");
        doNothing().when(user1).setDog((Dog) any());
        doNothing().when(user1).setFirstName((String) any());
        doNothing().when(user1).setId((Long) any());
        doNothing().when(user1).setLastName((String) any());
        doNothing().when(user1).setUserEmail((String) any());
        doNothing().when(user1).setUserPhoneNumber((String) any());

        user1.setDog(dog1);
        user1.setFirstName("Иван");
        user1.setId(5L);
        user1.setLastName("Иванов");
        user1.setUserEmail("ivan.ivanov@example.org");
        user1.setUserPhoneNumber("5555555555");

        assertThrows(UsersNullParameterValueException.class, () -> userService.createUserInDb(user1));
        verify(user1).setDog((Dog) any());
        verify(user1).setFirstName((String) any());
        verify(user1).setId((Long) any());
        verify(user1).setLastName((String) any());
        verify(user1).setUserEmail((String) any());
        verify(user1).setUserPhoneNumber((String) any());
    }


    @Test
    void testDeleteUserFirst() {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user = new User();
        user.setDog(dog);
        user.setFirstName("Иван");
        user.setId(5L);
        user.setLastName("Иванов");
        user.setUserEmail("ivan.ivanov@example.org");
        user.setUserPhoneNumber("5555555555");

        Optional<User> ofResult = Optional.of(user);

        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(userRepository).deleteById((Long) any());

        assertSame(user, userService.deleteUserById(5L));
        verify(userRepository).findById((Long) any());
        verify(userRepository).deleteById((Long) any());
    }


    @Test
    void testDeleteUserSecond() {
        when(userRepository.findById((Long) any())).thenThrow(new UsersNullParameterValueException("Message"));
        assertThrows(UsersNullParameterValueException.class, () -> userService.deleteUserById(5L));
        verify(userRepository).findById((Long) any());
    }


    @Test
    void testReplaceUserFirst() {
        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user1 = new User();
        user1.setDog(dog1);
        user1.setFirstName("Иван");
        user1.setId(5L);
        user1.setLastName("Иванов");
        user1.setUserEmail("ivan.ivanov@example.org");
        user1.setUserPhoneNumber("5555555555");

        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(user1));

        Dog dog2 = new Dog();
        dog2.setId(5L);
        dog2.setNickname("Гектор");
        dog2.setBreed("доберман");
        dog2.setAge(2);
        dog2.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user2 = new User();
        user1.setDog(dog2);
        user1.setFirstName("Иван");
        user1.setId(5L);
        user1.setLastName("Иванов");
        user1.setUserEmail("ivan.ivanov@example.org");
        user1.setUserPhoneNumber("5555555555");

        assertSame(user1, userService.replaceUserById(5L, user2));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testUpdateUserSecond() {
        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user1 = new User();
        user1.setDog(dog1);
        user1.setFirstName("Иван");
        user1.setId(5L);
        user1.setLastName("Иванов");
        user1.setUserEmail("ivan.ivanov@example.org");
        user1.setUserPhoneNumber("5555555555");

        when(userRepository.save((User) any())).thenThrow(new UsersNullParameterValueException("Message"));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(user1));

        assertThrows(UsersNullParameterValueException.class, () -> userService.replaceUserById(5L, user1));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testUpdateUserThird() {
        Dog dog1 = new Dog();
        dog1.setId(5L);
        dog1.setNickname("Гектор");
        dog1.setBreed("доберман");
        dog1.setAge(2);
        dog1.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        User user1 = new User();
        user1.setDog(dog1);
        user1.setFirstName("Иван");
        user1.setId(5L);
        user1.setLastName("Иванов");
        user1.setUserEmail("ivan.ivanov@example.org");
        user1.setUserPhoneNumber("5555555555");

        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());

        assertThrows(UsersNullParameterValueException.class, () -> userService.replaceUserById(5L, user1));
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testMatchingPhoneNumber() {
        assertEquals("+7(222)334-00-80", userService.MatchingPhoneNumber("72223340080"));
        assertNull(userService.MatchingPhoneNumber("89113214568794"));
    }

    @Test
    void testValidityEmail() {
        assertFalse(userService.ValidityEmail("E Mail"));
        assertTrue(userService.ValidityEmail("U@U"));
        assertFalse(userService.ValidityEmail(null));
    }

    @Test
    void testPatternMatches() {
        assertTrue(UserService.patternMatches("The String Being Checked", ".*"));
        assertFalse(UserService.patternMatches("The String Being Checked", "U"));
    }
}

