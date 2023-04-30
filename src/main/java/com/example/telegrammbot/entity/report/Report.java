package com.example.telegrammbot.entity.report;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The adopter daily report of the animal content, incl.
 * animal diet
 * animal well-being and health at new environment
 * animal change in behavior
 * actual animal photo
 *
 * @author zalex14
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "report")
public class Report {
    /**
     * The report's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The report's date & time
     */
    private LocalDateTime localDateTime;

    /**
     * pet's info (pet's name, adopter)
     */
    private String info;

    /**
     * pet's daily diet
     */
    private String diet;

    /**
     * general pet's health
     */
    private String health;

    /**
     * change of animal's behavior
     */
    private String behavior;

    /**
     * The telegram chat's id
     */
    private Long chatId;
    /**
     * The telegram chat's file id
     */
    private String telegramFileId;


    /**
     * The daily pet's photo
     */
    private byte[] photoFile;

    public Report(Long id, LocalDateTime localDateTime, String info, String diet, String health, String behavior,
                  Long chatId, String telegramFileId) {
        this.id = id;
        this.localDateTime = LocalDateTime.now();
        this.info = info;
        this.diet = diet;
        this.health = health;
        this.behavior = behavior;
        this.chatId = chatId;
        this.telegramFileId = telegramFileId;
    }
}