package com.iot.dto;

import com.iot.domain.enums.PlantType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Setter
public class PlantFullInfoDto {
    private Long id;
    private String plantName;
    private PlantType plantType;
    private float temperature;
    private float humidityAir;
    private float humiditySoil;
    private float light;
}
