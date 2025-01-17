package com.iot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PlantStatsDto {
    private float temperature;
    private float humidityAir;
    private float humiditySoil;
    private float light;
}
