package com.iot.controllers;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import com.iot.services.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("/smartPlantie")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

//    @GetMapping("/plants")
//    public List<Plant> getPlants(@RequestParam User user) {
//        try {
//            plantService.findByOwner(user);
//        }
//    }
}
