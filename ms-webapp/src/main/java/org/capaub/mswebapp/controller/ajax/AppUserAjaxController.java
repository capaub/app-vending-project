package org.capaub.mswebapp.controller.ajax;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.AppUserService;
import org.capaub.mswebapp.service.SessionService;
import org.capaub.mswebapp.service.dto.AppRoleDTO;
import org.capaub.mswebapp.service.dto.AppUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users/ajax")
public class AppUserAjaxController {
    private final AppUserService appUserService;
    private final SessionService sessionService;

    @PostMapping("create")
    public ModelAndView addUser(@RequestBody AppUserDTO appUserDTO, Model model) {
        Integer companyId = sessionService.getCompanyId();
        appUserDTO.setCompanyId(companyId);
        appUserService.addUser(appUserDTO);
        List<AppUserDTO> usersDTO = appUserService.getAllUsersByCompanyId(companyId);

        List<AppRoleDTO> roleList = List.of(
                new AppRoleDTO("ADMIN","administrateur"),
                new AppRoleDTO("STOCKER","approvisionneur"),
                new AppRoleDTO("USER","utilisateur")
        );

        model.addAttribute("authorities", roleList);

        return new ModelAndView("fragments/_users","users", usersDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<AppUserDTO> updateUser(@RequestBody AppUserDTO appUserDTO) {
        AppUserDTO updatedUser = appUserService.updateUser(appUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        appUserService.deleteUser(id);

    }
}