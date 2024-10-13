package org.capaub.mswebapp.service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.dto.AppUserDTO;
import org.capaub.mswebapp.service.client.CompanyClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionService {

    private final CompanyClient companyClient;
    private final HttpSession session;

    public AppUserDTO sessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        AppUserDTO appUserDTO = companyClient.getUserByEmail(currentUsername);

        if (appUserDTO != null && appUserDTO.getCompanyId() != null) {
            session.setAttribute("companyId", appUserDTO.getCompanyId());
        }
        return appUserDTO;
    }

    public Integer getCompanyId() {
        return (Integer) session.getAttribute("companyId");
    }

}