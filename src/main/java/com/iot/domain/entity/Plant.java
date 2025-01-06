package com.iot.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private String type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
