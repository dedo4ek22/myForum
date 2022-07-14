package com.kostApp.kostApp.models;

import org.hibernate.annotations.GeneratorType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "this field can`t be empty")
    @Size(max = 25, message = "name must be les then 25 characters")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "this field can`t be empty")
    @Size(max = 25, message = "surname must be les then 25 characters")
    private String surname;

    @Column(name = "age")
    @Min(value = 16, message = "you must be older then 16")
    private int age;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    @NotBlank(message = "this field can`t be empty")
    @Size(min = 8, message = "password can`t be les then 8 character")
    private String password;

    @Column(name = "nik")
    @NotBlank(message = "this field can`t be empty")
    @Size(max = 30, message = "nikname can`t be more then 30 character")
    private String nik;

    @Column(name = "email")
    @Email
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Discussion> discussions;


    public User() {
    }

    public User(String name, String surname, int age, String password, String nik, String email) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.password = password;
        this.nik = nik;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", nik='" + nik + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
