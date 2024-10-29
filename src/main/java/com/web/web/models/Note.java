package com.web.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "note")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private String data;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "{" +
                "id:" + "\"" + id + "\"" + "," + "\'" +
                "data:" + "\"" + data + "\"" + "\'" +
                '}';
    }
}
