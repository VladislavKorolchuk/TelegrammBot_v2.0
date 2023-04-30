package com.example.telegrammbot.entity;

import javax.persistence.*;


/**
 * Entity: User
 *
 * @author Loginova Viktoria
 * @version 1.0.0
 */
@Entity                 // добавил Ю.Калынбаев
@Table(name = "users") // добавил Ю.Калынбаев
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy
    @Column(name = "id")
    private Long id;                 // Identifier

    @Column(name = "first_name")
    private String firstName;       // Username

    @Column(name = "last_name")
    private String lastName;        //User last name

    @Column(name = "user_phone_number")
    private String userPhoneNumber; // User phone

    @Column(name = "user_email")
    private String userEmail;        // User e-mail


    // добавил Ю.Калынбаев
    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;
    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Cat cat;
    // добавил Ю.Калынбаев

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }
}