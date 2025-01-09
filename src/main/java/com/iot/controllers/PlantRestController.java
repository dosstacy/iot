package com.iot.controllers;

import com.iot.dto.PlantInfoDto;
import com.iot.services.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plant")
public class PlantRestController {
    private final PlantService plantService;

    @GetMapping("/info")
    public Optional<PlantInfoDto> findFirstByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return plantService.findFirstByOwnerUsername(username);
    }
}
