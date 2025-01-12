package com.iot.repository;

import com.iot.domain.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findUserByPlantsId(Long plantsId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.currentPlantId = :plantsId WHERE u.id = :userId")
    void updateCurrentPlantId(@Param("userId") Long userId, @Param("plantsId") Long plantsId);
}
