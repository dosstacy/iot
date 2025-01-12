package com.iot.repository;

import com.iot.domain.entity.Plant;
import com.iot.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends CrudRepository<Plant, Long> {
    Optional<Plant> findByName(String name);

    List<Plant> findByOwner(User owner);

    List<Plant> findAllByOwnerUsername(String username);

    Optional<Plant> findFirstByOwnerUsername(String username);

    boolean existsByOwnerUsername(String username);

    @Query("SELECT p.id FROM Plant p WHERE p.name = :name")
    Long findIdByName(@Param("name") String name);

    Optional<Plant> findPlantByOwnerCurrentPlantId(Long id);

}
