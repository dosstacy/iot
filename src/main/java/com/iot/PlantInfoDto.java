package com.iot;

import com.iot.domain.enums.PlantType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PlantInfoDto {
    private String plantName;
    private PlantType plantType;
}
