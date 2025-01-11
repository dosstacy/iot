package com.iot.services;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import com.iot.domain.exceptions.InvalidUserException;
import com.iot.domain.exceptions.PlantsException;
import com.iot.dto.PlantInfoDto;
import com.iot.repository.PlantRepository;
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

    public Optional<PlantInfoDto> findFirstByOwnerUsername(String username) {
        return plantRepository.findFirstByOwnerUsername(username)
                .map(plant -> modelMapper.map(plant, PlantInfoDto.class));
    }

    public boolean userHasPlants(String username) {
        return plantRepository.existsByOwnerUsername(username);
    }
}
