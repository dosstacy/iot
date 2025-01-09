package com.iot.controllers;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import com.iot.dto.PlantInfoDto;
import com.iot.services.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller("/smartPlantie")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

//    @GetMapping("/plants")
//    public Optional<Plant> getPlants(@RequestBody User user) {
//        try {
//            return plantService.findByOwner(user);
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//            return Optional.empty();
//        }
//    }

//    @GetMapping
//    public String getPlants(Model model, User user) {
//        List<PlantInfoDto> plants = plantService.findByOwner(user);
//        model.addAttribute("plants", plants);
//        return "stats";
//    }
}
