package com.iot.utils;

import com.iot.services.PlantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final PlantService plantService;

    public CustomAuthenticationSuccessHandler(PlantService plantService) {
        this.plantService = plantService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = authentication.getName();

        if (!plantService.userHasPlants(username)) {
            response.sendRedirect("/smartPlantie/plantType");
        } else {
            response.sendRedirect("/smartPlantie/stats");
        }
    }
}

