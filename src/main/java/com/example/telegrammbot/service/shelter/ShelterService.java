package com.example.telegrammbot.service.shelter;

import js6team3.tbot.entity.shelter.Shelter;
import js6team3.tbot.exception.NullValueException;
import js6team3.tbot.repository.shelter.ShelterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for managing the shelter information
 *
 * @author zalex14
 */
@Service
public class ShelterService {
    private final Logger logger = LoggerFactory.getLogger(ShelterService.class);

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    /**
     * Add new shelter
     *
     * @param shelter obj
     * @return new shelter
     * @throws NullValueException if the shelter's id isn't exist
     */
    public Shelter addShelter(Shelter shelter) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Current Method is - " + methodName);

        if (shelter.getName().isBlank() || shelter.getName() == null) {
            throw new NullValueException("Имя приюта не задано");
        }
        return this.shelterRepository.save(shelter);
    }

    /**
     * Delete the shelter
     *
     * @param id The shelter id
     */
    @CacheEvict("shelter")
    public void deleteShelter(Long id) {
        shelterRepository.deleteById(id);
    }

    /**
     * Edit the shelter data
     *
     * @param id      the shelter id
     * @param shelter obj
     * @return the edited shelter
     * @throws NullValueException if the shelter's id isn't exist
     */
    @CachePut(value = "shelter", key ="#shelter.id")
    public Shelter editShelter(Long id, Shelter shelter) {
        String methodName = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();
        logger.info("Current Method is - " + methodName);

        Shelter editShelter = shelterRepository.findById(id).orElseThrow(null);
        if (editShelter != null) {
            editShelter.setName(shelter.getName());
            editShelter.setInformation(shelter.getInformation());

        } else {
            throw new NullValueException("Недостаточно данных для обновления информации о приюте");
        }
        return shelterRepository.save(editShelter);

    }

    /**
     * Outpoint of shelters' list
     */
    public Collection<Shelter> getAllShelters() {
        return this.shelterRepository.findAll();
    }

    /**
     * The shelter's info
     */
    @Cacheable("shelter")
    public Shelter getShelter(Long id) {
        return shelterRepository.findById(id).orElseThrow(null);
    }
}