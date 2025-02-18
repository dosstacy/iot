package com.iot.domain.entity;

import com.iot.domain.enums.PlantType;
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
    private String name;
    @Enumerated(EnumType.STRING)
    private PlantType type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    private float temperature;
    private float humidityAir;
    private float humiditySoil;
    private float light;
}
