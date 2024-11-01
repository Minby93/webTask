package com.web.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    @Column(name = "data", columnDefinition = "TEXT")
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
