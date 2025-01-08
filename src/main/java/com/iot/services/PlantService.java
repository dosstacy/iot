package com.iot.services;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import com.iot.domain.exceptions.InvalidUserException;
import com.iot.domain.exceptions.PlantsException;
import com.iot.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;

    public Plant getById(Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new PlantsException("Cannot get plant with id " + id));
    }

    public Plant getByName(String name) {
        return plantRepository.findByName(name)
                .orElseThrow(() -> new PlantsException("Cannot get plant with name " + name));
    }

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

    public void delete(Long id) {
        if (!plantRepository.existsById(id)) {
            throw new InvalidUserException("Cannot delete plant with id " + id);
        }
        plantRepository.deleteById(id);
    }
}
