package com.iot.services;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import com.iot.domain.exceptions.InvalidUserException;
import com.iot.domain.exceptions.PlantsException;
import com.iot.dto.PlantFullInfoDto;
import com.iot.dto.PlantInfoDto;
import com.iot.dto.PlantStatsDto;
import com.iot.repository.PlantRepository;
import com.iot.repository.UserRepository;
import com.iot.utils.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Plant getById(Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new PlantsException("Cannot get plant with id " + id));
    }

    public Plant getByName(String name) {
        return plantRepository.findByName(name)
                .orElseThrow(() -> new PlantsException("Cannot get plant with name " + name));
    }

    public List<Plant> findByOwner(User user) {
        try {
            return plantRepository.findByOwner(user);
        } catch (Exception e) {
            throw new PlantsException("Cannot get plants with owner " + user.getUsername());
        }
    }

    public void save(PlantInfoDto plantDto) {
        Plant plant;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
            User user = customUserDetails.getUser();
            plant = modelMapper.map(plantDto, Plant.class);

            if(plantRepository.existsByNameAndOwner(plant.getName(), user)) {
                throw new PlantsException("Plant with name " + plant.getName() + " already exists");
            }

            plant.setOwner(user);
            plantRepository.save(plant);
        } catch (Exception e) {
            throw new PlantsException("Cannot save plant " + e);
        }
    }

    public void delete(Long id) {
        if (!plantRepository.existsById(id)) {
            throw new InvalidUserException("Cannot delete plant with id " + id);
        }
        plantRepository.deleteById(id);
    }

    public List<PlantInfoDto> getAllPlants(String username) {
        return plantRepository.findAllByOwnerUsername(username)
                .stream()
                .map(plant -> modelMapper.map(plant, PlantInfoDto.class))
                .toList();
    }

    public boolean userHasPlants(String username) {
        return plantRepository.existsByOwnerUsername(username);
    }

    public Long findIdByName(String name) {
        return plantRepository.findIdByName(name);
    }

    public void updatePlantStats(Long plantId, String type, float data) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(() -> new PlantsException("Cannot update plant with id " + plantId));
        log.info("Type of data: {}", type);
        switch (type) {
            case "temperature" -> plant.setTemperature(data);
            case "humidity_soil" -> plant.setHumiditySoil(data);
            case "humidity_air" -> plant.setHumidityAir(data);
            default -> plant.setLight(data);
        }
        try {
            plantRepository.save(plant);
        } catch (Exception e) {
            throw new PlantsException("Cannot save plant " + e);
        }
    }

    public Optional<PlantFullInfoDto> findPlantByCurrentPlantIdOrFirst(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

        if (customUser.getUser().getCurrentPlantId() == null) {
            log.info("Is current plant id null? = {}", customUser.getUser().getCurrentPlantId());
            return findFirstByOwnerUsername(customUser.getUsername());
        }
        log.info("result from findPlantByCurrentPlantIdOrFirst: {}", findPlantByCurrentPlantId(customUser.getUser().getCurrentPlantId()));
        return findPlantByCurrentPlantId(customUser.getUser().getCurrentPlantId());
    }

    public Optional<PlantFullInfoDto> findPlantByCurrentPlantId(Long plantId) {
        return plantRepository.findById(plantId)
                .map(plant -> modelMapper.map(plant, PlantFullInfoDto.class));
    }

    public Optional<PlantFullInfoDto> findFirstByOwnerUsername(String username) {
        return plantRepository.findFirstByOwnerUsername(username)
                .map(plant -> modelMapper.map(plant, PlantFullInfoDto.class));
    }

    public Optional<PlantStatsDto> getPlantStats(Long id){
        return plantRepository.findById(id)
                .map(plant -> modelMapper.map(plant, PlantStatsDto.class));
    }
}
