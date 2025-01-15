package com.iot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlantStatsDto {
    private Long id;
    private String temperature;
    private String soilHumidity;
    private String airHumidity;
    private String light;
}
