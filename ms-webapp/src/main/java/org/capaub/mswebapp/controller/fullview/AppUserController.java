package org.capaub.mswebapp.controller.fullview;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.AppUserService;
import org.capaub.mswebapp.service.dto.AppRoleDTO;
import org.capaub.mswebapp.service.dto.AppUserDTO;
import org.capaub.mswebapp.service.dto.CompleteRegistrationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping("/index")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/signUp")
    public ModelAndView signup() {
        return new ModelAndView("signUp","completeRegistrationDTO", new CompleteRegistrationDTO());
    }

    @PostMapping("/signUp")
    public ModelAndView signUp(@ModelAttribute CompleteRegistrationDTO completeRegistrationDTO) {
        appUserService.completeRegistration(completeRegistrationDTO);

        return new ModelAndView("signIn");
    }

    @GetMapping("/users/getAllUsersByCompanyId")
    public ModelAndView showCustomers(HttpSession session, Model model) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        List<AppUserDTO> usersDTO = appUserService.getAllUsersByCompanyId(companyId);

        List<AppRoleDTO> roleList = List.of(
                new AppRoleDTO("ADMIN","administrateur"),
                new AppRoleDTO("STOCKER","approvisionneur"),
                new AppRoleDTO("USER","utilisateur")
        );
        model.addAttribute("authorities", roleList);
        model.addAttribute("fragmentPath","fragments/users");

        return new ModelAndView("index","users", usersDTO);
    }

    @GetMapping("/createPassword")
    public ModelAndView showPasswordSetupPage(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return new ModelAndView("createPassword");
    }

    @PostMapping("/password-setup")
    public String setPassword(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        appUserService.updatePassword(username, password);
        return "redirect:/signIn?passwordSet";
    }

}