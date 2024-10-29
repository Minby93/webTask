package com.web.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "myuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Note> notes = new ArrayList<>();

    @Override
    public String toString() {
        return "{" +
                "id:" + '\"' + id + '\"' + ',' + '\'' +
                "username:" + '\"' + username + '\"' + ',' + '\'' +
                "password:" + '\"' + password + '\"' + ',' + '\'' +
                '}';
    }
}
