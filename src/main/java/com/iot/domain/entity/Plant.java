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
    @Column(unique = true)
    private String name;
    private PlantType type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    private Float temperature;
    @Column(name = "air_humidity")
    private Float airHumidity;
    @Column(name = "soil_humidity")
    private Float soilHumidity;
    private Long light;
}
