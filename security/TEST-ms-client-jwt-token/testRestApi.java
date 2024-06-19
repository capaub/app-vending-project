package org.capaub.patient;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class testRestApi {

    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public Map<String,Object> test(Authentication authentication) {
        return Map.of(
                "name","aubry",
                "username",authentication.getName(),
                "scope",authentication.getAuthorities());
    }
}
