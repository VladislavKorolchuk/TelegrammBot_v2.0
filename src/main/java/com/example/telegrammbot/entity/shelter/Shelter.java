package com.example.telegrammbot.entity.shelter;

import lombok.Data;

import javax.persistence.*;

/**
 * The shelter general description
 *
 * @author zalex14
 */
@Data
@Entity
public class Shelter {
    /**
     * The shelter id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The shelter name
     */
    private String name;

    /**
     * The shelter general information
     */
    private String information;

    /**
     * The shelter address
     */
    private String address;

    /**
     * The shelter contact's phone
     */
    private String phone;

    /**
     * driving_link
     */
    private String drivingLink;

    /**
     * working_hours
     */
    private String workingHours;

    /**
     * territory_admission
     */
    private String territoryAdmission;

    /**
     * territory_staying
     */
    private String territoryStaying;

    /**
     * security_contacts
     */
    private String securityContacts;

    /**
     * animal_acquaintance
     */
    private String animalAcquaintance;

    /**
     * document_list
     */
    private String documentList;

    /**
     * travel_recommendation
     */
    private String travelRecommendation;

    /**
     * child_arranging
     */
    private String childArranging;

    /**
     * adult_arranging
     */
    private String adultArranging;

    /**
     * sick_arrangement
     */
    private String sickArrangement;

    /**
     * handler_advice
     */
    private String handlerAdvice;

    /**
     * refusal_reason
     */
    private String refusalReason;
}