package com.iot.controllers;

import com.iot.dto.PlantInfoDto;
import com.iot.services.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plant")
public class PlantRestController {
    private PlantService plantService;

    @GetMapping("/info")
    public PlantInfoDto getPlantInfo(@RequestParam String name) {
        return plantService.getPlantInfo(name);
    }
}
