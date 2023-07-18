package com.example.test.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String points;

    public Point(String points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Point{id=%d, points=%s}".formatted(id, points);
    }
}
