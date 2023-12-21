package com.cloud.backup.system.model.impl;


import com.cloud.backup.system.model.Model;
import jakarta.persistence.*;


@Table(schema = "cloud",name = "TB_USER")
@Entity
public class User implements Model {

    public User() {}

    public User(Long id, String email, String password, String name, UserRoles userRoles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRoles = userRoles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column
    private String name;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles userRoles;


    @Override
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }
}
