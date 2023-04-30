package com.example.telegrammbot.service;

import js6team3.tbot.entity.Cat;
import js6team3.tbot.entity.CatPhoto;
import js6team3.tbot.repository.CatPhotoRepository;
import js6team3.tbot.repository.CatRepository;
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
 * Тестирование сервиса для работы с сущностью "CatPhoto"
 *
 * @author Юрий Калынбаев
 */
@ContextConfiguration(classes = {CatPhotoService.class})
@ExtendWith(SpringExtension.class)
class CatPhotoServiceTest {
    @MockBean
    private CatPhotoRepository catPhotoRepository;

    @MockBean
    private CatPhotoService catPhotoService;

    @MockBean
    private CatRepository catRepository;

    @Value("${dir.path.PhotoCat}")
    private String photoCatDir;

    @Test
    void testUploadPhotoCat() throws Exception {

        Long id = 7L;

        Cat cat = new Cat();
        cat.setId(id);
        cat.setNickname("Артемида");
        cat.setBreed("Бенгальская кошка");
        cat.setAge(3);
        cat.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

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
        Path filePath = Path.of(photoCatDir, id + "." + path);

        CatPhoto catPhoto = new CatPhoto();
        catPhoto.setCat(cat);
        catPhoto.setFilePath(filePath.toString());
        catPhoto.setFileSize(5L);
        catPhoto.setId(id);
        catPhoto.setMediaType("Media Type");

        when(catPhotoRepository.findById((Long) any())).thenReturn(Optional.of(catPhoto));
        when(catPhotoService.uploadPhotoCat(any(Long.class), any(MultipartFile.class))).thenReturn(catPhoto);

        assertSame(catPhoto, catPhotoService.uploadPhotoCat(id, result));
    }

    @Test
    void getExtensionsTest() throws Exception {
        String name1 = "Name";
        String name2 = "NameFile.Name";
        String s = name2.substring(name2.lastIndexOf(".") + 1);

        when(catPhotoService.getExtensions((String) any())).thenReturn(s);

        assertEquals(name1, catPhotoService.getExtensions(s));
        verify(catPhotoService).getExtensions((String) any());
    }


    @Test
    void testFindCatById() throws Exception {
        Cat cat = new Cat();
        cat.setId(7L);
        cat.setNickname("Артемида");
        cat.setBreed("Бенгальская кошка");
        cat.setAge(3);
        cat.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        CatPhoto catPhoto = new CatPhoto();
        catPhoto.setCat(cat);
        catPhoto.setFilePath("/directory/foo.txt");
        catPhoto.setFileSize(5L);
        catPhoto.setId(7L);
        catPhoto.setMediaType("Media Type");

        when(catPhotoService.findCatById((Long) any())).thenReturn(cat);

        assertSame(cat, catPhotoService.findCatById(7L));
        verify(catPhotoService).findCatById((Long) any());
    }

    @Test
    void testFindPhotoCat() throws Exception {
        Cat cat = new Cat();
        cat.setId(7L);
        cat.setNickname("Артемида");
        cat.setBreed("Бенгальская кошка");
        cat.setAge(3);
        cat.setDescription("Это – элитные домашние животные для людей с особыми запросами," +
                " уверенно покоряющие сердца «кошатников» по всему земному шару и отличающиеся: грациозностью," +
                " невероятным умом, разговорчивостью и активностью… а их повадки чем-то напоминают собачьи.");

        CatPhoto catPhoto = new CatPhoto();
        catPhoto.setCat(cat);
        catPhoto.setFilePath("/directory/foo.txt");
        catPhoto.setFileSize(5L);
        catPhoto.setId(7L);
        catPhoto.setMediaType("Media Type");

        when(catPhotoService.findPhotoCat((Long) any())).thenReturn(catPhoto);

        assertSame(catPhoto, catPhotoService.findPhotoCat(7L));
        verify(catPhotoService).findPhotoCat((Long) any());
    }
}