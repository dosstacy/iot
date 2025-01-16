package com.iot.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.domain.exceptions.PlantNotFound;
import com.iot.dto.PlantInfoDto;
import com.iot.services.PlantService;
import com.iot.services.UserService;
import com.iot.utils.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public Optional<PlantInfoDto> findPlantByCurrentPlantIdOrFirst() {
        return plantService.findPlantByCurrentPlantIdOrFirst();
    }

    @GetMapping
    public List<PlantInfoDto> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return plantService.getAllPlants(username);
    }

    @PostMapping("/save")
    public void save(@RequestBody PlantInfoDto plantInfoDto) {
        plantService.save(plantInfoDto);
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

    @PutMapping("/data")
    public void updatePlantStats(@RequestBody String jsonBody) {
        Optional<PlantInfoDto> plant = plantService.findPlantByCurrentPlantIdOrFirst();
        String plantName = plant.get().getPlantName();

        if (plantName == null) {
            throw new PlantNotFound("Cannot find plant because its name is null");
        }

        Long plantId = plantService.findIdByName(plantName);
        String dataType = null;
        String value = null;

        log.info("This is jsonBody: {}", jsonBody);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonBody);
            dataType = root.path("metrics").get(0).path("name").asText();
            value = root.path("metrics").get(0).path("value").asText();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        if (dataType != null && value != null) {
            plantService.updatePlantStats(plantId, dataType, Float.parseFloat(value));
        }
    }
}
