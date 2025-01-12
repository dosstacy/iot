package com.iot.repository;

import com.iot.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findUserByPlantsId(Long plantsId);
    void updateCurrentPlantId(Long userId, Long plantsId);
}
