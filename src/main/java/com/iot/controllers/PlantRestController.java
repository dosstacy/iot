package com.iot.controllers;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

        if(customUser.getUser().getCurrentPlantId() == null){
            log.info("Is current plant id null? = {}", customUser.getUser().getCurrentPlantId());
            return plantService.findFirstByOwnerUsername(customUser.getUsername());
        }
        log.info("result from findPlantByCurrentPlantIdOrFirst: {}", plantService.findPlantByCurrentPlantId(customUser.getUser().getCurrentPlantId()));
        return plantService.findPlantByCurrentPlantId(customUser.getUser().getCurrentPlantId());
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
}
