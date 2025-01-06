package com.iot.services;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import com.iot.domain.exceptions.PlantsException;
import com.iot.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;

    public Optional<Plant> findByOwner(User user) {
        try {
            return plantRepository.findByOwner(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void save(Plant plant) {
        try {
            plantRepository.save(plant);
        }catch (Exception e) {
            throw new PlantsException("Cannot save plant " + plant);
        }
    }
}
