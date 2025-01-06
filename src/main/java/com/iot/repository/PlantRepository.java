package com.iot.repository;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlantRepository extends CrudRepository<Plant, Long> {
    Optional<Plant> findByOwner(User owner);
}
