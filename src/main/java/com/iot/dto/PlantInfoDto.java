package com.iot.dto;

import com.iot.domain.enums.PlantType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PlantInfoDto {
    private Long id;
    private String plantName;
    private PlantType plantType;
}
