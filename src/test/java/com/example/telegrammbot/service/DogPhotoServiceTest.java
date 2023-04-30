package com.example.telegrammbot.service;

import js6team3.tbot.entity.Dog;
import js6team3.tbot.entity.DogPhoto;
import js6team3.tbot.repository.DogPhotoRepository;
import js6team3.tbot.repository.DogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестирование сервиса для работы с сущностью "DogPhoto"
 *
 * @author Юрий Калынбаев
 */
@ContextConfiguration(classes = {DogPhotoService.class})
@ExtendWith(SpringExtension.class)
class DogPhotoServiceTest {
    @MockBean
    private DogPhotoRepository dogPhotoRepository;

    @MockBean
    private DogPhotoService dogPhotoService;

    @MockBean
    private DogRepository dogRepository;

    @Value("${dir.path.PhotoDog")
    private String photoDogDir;

    @Test
    void testUploadPhotoDog() throws Exception {

        Long id = 5L;

        Dog dog = new Dog();
        dog.setId(id);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        Path path = Paths.get("/path/to/the/file.txt");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        Path filePath = Path.of(photoDogDir, id + "." + path);

        DogPhoto dogPhoto = new DogPhoto();
        dogPhoto.setDog(dog);
        dogPhoto.setFilePath(filePath.toString());
        dogPhoto.setFileSize(5L);
        dogPhoto.setId(id);
        dogPhoto.setMediaType("Media Type");

        when(dogPhotoRepository.findById((Long) any())).thenReturn(Optional.of(dogPhoto));
        when(dogPhotoService.uploadPhotoDog(any(Long.class), any(MultipartFile.class))).thenReturn(dogPhoto);

        assertSame(dogPhoto, dogPhotoService.uploadPhotoDog(id, result));
    }

    @Test
    void getExtensionsTest() throws Exception {
        String name1 = "Name";
        String name2 = "NameFile.Name";
        String s = name2.substring(name2.lastIndexOf(".") + 1);

        when(dogPhotoService.getExtensions((String) any())).thenReturn(s);

        assertEquals(name1, dogPhotoService.getExtensions(s));
        verify(dogPhotoService).getExtensions((String) any());
    }


    @Test
    void testFindDogById() throws Exception {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        DogPhoto dogPhoto = new DogPhoto();
        dogPhoto.setDog(dog);
        dogPhoto.setFilePath("/directory/foo.txt");
        dogPhoto.setFileSize(5L);
        dogPhoto.setId(5L);
        dogPhoto.setMediaType("Media Type");

        when(dogPhotoService.findDogById((Long) any())).thenReturn(dog);

        assertSame(dog, dogPhotoService.findDogById(5L));
        verify(dogPhotoService).findDogById((Long) any());
    }

    @Test
    void testFindPhotoDog() throws Exception {
        Dog dog = new Dog();
        dog.setId(5L);
        dog.setNickname("Гектор");
        dog.setBreed("доберман");
        dog.setAge(2);
        dog.setDescription("Они энергичны и нуждаются в активности, прогулках, нагрузках.");

        DogPhoto dogPhoto = new DogPhoto();
        dogPhoto.setDog(dog);
        dogPhoto.setFilePath("/directory/foo.txt");
        dogPhoto.setFileSize(5L);
        dogPhoto.setId(5L);
        dogPhoto.setMediaType("Media Type");

        when(dogPhotoService.findPhotoDog((Long) any())).thenReturn(dogPhoto);

        assertSame(dogPhoto, dogPhotoService.findPhotoDog(5L));
        verify(dogPhotoService).findPhotoDog((Long) any());
    }
}