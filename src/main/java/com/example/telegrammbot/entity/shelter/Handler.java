package com.example.telegrammbot.entity.shelter;

import lombok.Data;

import javax.persistence.*;

/**
 * The recommended handlers listing
 *
 * @author zalex14
 */
@Data
@Entity
public class Handler {
    /**
     * The handler id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The handler surname
     */
    private String surname;

    /**
     * The handler contact's phone
     */
    private String contact;

    /**
     * The handler call_time
     */
    private String call_time;

    /**
     * The handler company
     */
    private String company;

    /**
     * The handler add_info (diploma, prizes, recommendations, etc.)
     */
    private String add_info;
}