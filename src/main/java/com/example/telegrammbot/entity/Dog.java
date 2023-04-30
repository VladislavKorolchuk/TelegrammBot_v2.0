package com.example.telegrammbot.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность: Dog
 *
 * @author Юрий Калынбаев
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dogs")
public class Dog {

    /**
     * поле "id" - идентификатор экземпляра класса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * поле "nickname" - кличка
     */
    @Column(name = "nick_name")
    private String nickname;

    /**
     * поле "breed" - порода
     */
    @Column(name = "breed")
    private String breed;

    /**
     * поле "age" - возраст
     */
    @Column(name = "age")
    private int age;

    /**
     * поле "description" - описание
     */
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.TRUE)
    List<User> user;

    public void setAge(int age) {
        if (age < 0) {                 // Проверка на отрицательное число
            age *= -1;                 // Инвентаризация числа
        } else if (age > 30) {         // Проверка на максимальный возраст
            age = 0;
        }
        this.age = age;
    }
}
