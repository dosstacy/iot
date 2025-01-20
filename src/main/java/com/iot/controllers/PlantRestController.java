package com.iot.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.dto.PlantFullInfoDto;
import com.iot.dto.PlantInfoDto;
import com.iot.dto.PlantStatsDto;
import com.iot.services.PlantService;
import com.iot.services.UserService;
import com.iot.utils.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.iot.domain.exceptions.PlantsException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plants")
@Slf4j
public class PlantRestController {
    private final PlantService plantService;
    private final UserService userService;

    @GetMapping("/info")
    public Optional<PlantFullInfoDto> getPlantStats() {
        Optional<PlantFullInfoDto> dto = findPlantByCurrentPlantIdOrFirst();
        Long id = dto.get().getId();

        Optional<PlantStatsDto> stats = plantService.getPlantStats(id);

        dto.get().setTemperature(stats.get().getTemperature());
        dto.get().setHumiditySoil(stats.get().getHumiditySoil());
        dto.get().setHumidityAir(stats.get().getHumidityAir());
        dto.get().setLight(stats.get().getLight());

        return dto;
    }

    public Optional<PlantFullInfoDto> findPlantByCurrentPlantIdOrFirst() {
        return plantService.findPlantByCurrentPlantIdOrFirst();
    }

    @GetMapping
    public List<PlantInfoDto> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return plantService.getAllPlants(username);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PlantInfoDto plantInfoDto) {
        try {
            plantService.save(plantInfoDto);
            return ResponseEntity.ok("Plant saved successfully!");
        } catch (PlantsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/plant")
    public void selectPlant(@RequestParam String plantName) {
        log.info("Plant name in plant: {}", plantName);
        Long plantId = plantService.findIdByName(plantName);
        log.info("Plant id is: {}", plantId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
        customUser.getUser().setCurrentPlantId(plantId);
        userService.updateCurrentPlantId(customUser.getUser().getId(), customUser.getUser().getCurrentPlantId());
        log.info("Current plant id {}", customUser.getUser().getCurrentPlantId());
    }

    @DeleteMapping("/bin")
    public void deletePlant(@RequestParam String plantName) {
        Long plantId = plantService.findIdByName(plantName);
        plantService.delete(plantId);
    }

//    @PutMapping("/stats")
//    public void updatePlantStats(@RequestBody String jsonBody) {
//        Optional<PlantFullInfoDto> plant = plantService.findPlantByCurrentPlantIdOrFirst();
//        Long plantId = plant.get().getId();
//
//        String dataType = null;
//        String value = null;
//
//        log.info("This is jsonBody: {}", jsonBody);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode root = objectMapper.readTree(jsonBody);
//            dataType = root.path("name").asText();
//            value = root.path("value").asText();
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//
//        if (dataType != null || value != null) {
//            plantService.updatePlantStats(plantId, dataType, Float.parseFloat(value));
//        }
//    }

    @PutMapping("/stats")
    public void updatePlantStats(@RequestBody PlantStatsDto statsDTO) {
        Optional<PlantFullInfoDto> plant = plantService.findPlantByCurrentPlantIdOrFirst();
        Long plantId = plant.get().getId();

        log.info("Received stats update: {}", statsDTO);
        plantService.updatePlantStats(plantId, statsDTO);
    }

}
