package com.kostApp.kostApp.DTO;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {

    private int id;

    @NotBlank(message = "this field can`t be empty")
    @Size(max = 25, message = "name must be les then 25 characters")
    private String name;

    @NotBlank(message = "this field can`t be empty")
    @Size(max = 25, message = "surname must be les then 25 characters")
    private String surname;

    @Min(value = 16, message = "you must be older then 16")
    private int age;

    @NotBlank(message = "this field can`t be empty")
    @Size(min = 8, message = "password can`t be les then 8 character")
    private String password;

    @NotBlank(message = "this field can`t be empty")
    @Size(max = 30, message = "nikname can`t be more then 30 character")
    private String nik;

    @Email
    private String email;

    public UserDTO() {
    }

    public UserDTO(String name, String surname, int age, String password, String nik, String email) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.password = password;
        this.nik = nik;
        this.email = email;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
