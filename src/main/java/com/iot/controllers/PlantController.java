package com.iot.controllers;

import com.iot.services.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("/smartPlantie")
@RequiredArgsConstructor
public class PlantController {
    private PlantService plantService;

}
