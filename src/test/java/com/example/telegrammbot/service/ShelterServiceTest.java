package com.example.telegrammbot.service;

import com.example.telegrammbot.entity.shelter.Shelter;
import com.example.telegrammbot.repository.shelter.ShelterRepository;
import com.example.telegrammbot.service.shelter.ShelterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * The mockito tests for the shelter service
 *
 * @author zalex14
 */
@ContextConfiguration(classes = {ShelterService.class})
@ExtendWith(SpringExtension.class)
public class ShelterServiceTest {
    public static final Long ID = 5L;
    public static final String SHELTER_NAME = "Приют Кошкин дом";
    public static final String SHELTER_INFO = "Приют для бездомных кошек и котят для лисоковчан с добрыми сердцами!";
    @MockBean
    private ShelterRepository shelterRepository;

    @Autowired
    private ShelterService shelterService;

    /**
     * Test to get all shelters
     */
    @Test
    void shouldReturnWhenGetShelters() {
        ArrayList<Shelter> allShelters = new ArrayList<>();
        when(shelterRepository.findAll()).thenReturn(allShelters);

        Collection<Shelter> actualAllShelters = shelterService.getAllShelters();

        assertSame(allShelters, actualAllShelters);
        assertTrue(actualAllShelters.isEmpty());
        verify(shelterRepository).findAll();
    }

    /**
     * Test to add for new shelter
     */
    @Test
    void shouldReturnWhenAddShelter() {
        Shelter shelter = new Shelter();
        shelter.setId(ID);
        shelter.setName(SHELTER_NAME);
        shelter.setInformation(SHELTER_INFO);

        Mockito.when(shelterRepository.save(shelter)).thenReturn(shelter);
        Shelter createdShelter = shelterService.addShelter(shelter);
        Mockito.verify(shelterRepository, Mockito.times(1)).save(shelter);
        Assertions.assertEquals(shelter.getId(), createdShelter.getId());
        Assertions.assertEquals(shelter.getName(), createdShelter.getName());
        Assertions.assertEquals(shelter.getInformation(), createdShelter.getInformation());

    }

    /**
     * Test to edit the shelter
     */
    @Test
    void shouldReturnWhenEditShelter() {
        Shelter shelter0 = new Shelter();
        shelter0.setId(ID);
        shelter0.setName(SHELTER_NAME);
        shelter0.setInformation(SHELTER_INFO);

        when(shelterRepository.save(any())).thenReturn(shelter0);
        when(shelterRepository.findById(any())).thenReturn(Optional.of(shelter0));

        Shelter shelter1 = new Shelter();
        shelter1.setId(ID);
        shelter1.setName(SHELTER_NAME);
        shelter1.setInformation(SHELTER_INFO);

        assertSame(shelter0, shelterService.editShelter(ID, shelter1));
        verify(shelterRepository).save(any());
        verify(shelterRepository).findById(any());
    }

    /**
     * Test to delete the shelter
     */
    @Test
    void shouldReturnWhenDeleteShelter() {
        ShelterRepository shelterRepository = mock(ShelterRepository.class);
        doNothing().when(shelterRepository).deleteById(ID);
        ShelterService shelterService = new ShelterService(shelterRepository);
        shelterService.deleteShelter(ID);
        Mockito.verify(shelterRepository, Mockito.times(1)).deleteById(ID);
    }
}