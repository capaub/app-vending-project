package org.capaub.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestApiControllerTest {

    @GetMapping("/dataTest")
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    public Map<String, Object> testRestApi(Authentication authentication) {
        return Map.of(
                "message","Data test",
                "username",authentication.getName(),
                "authorities",authentication.getAuthorities()
        );
    }

    @PostMapping("/saveData")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public Map<String, Object> saveData(Authentication authentication, String data) {
        return Map.of(
                "dataSaved", data
        );
    }
}
