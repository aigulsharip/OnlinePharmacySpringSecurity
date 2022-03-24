package kz.aigulsharip.springboot.security.controller;

import kz.aigulsharip.springboot.security.model.AuthUser;
import kz.aigulsharip.springboot.security.model.Medication;
import kz.aigulsharip.springboot.security.service.PharmacyService;
import kz.aigulsharip.springboot.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SecurityController extends BaseController{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final PharmacyService pharmacyService;


    @GetMapping(value = "/")
    public String mainPage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        List<Medication> medications = pharmacyService.getAllMedications();
        model.addAttribute("medications", medications);

        return "medications";
        //return "index";
    }

    @GetMapping(value = "/signinpage")
    public String signInPage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "signinpage";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "profile";
    }

    @GetMapping(value = "/adminpanel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String adminPanel(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "adminpanel";
    }

    @GetMapping(value = "/moderatorpanel")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String moderatorPanel(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "moderatorpanel";
    }

    @GetMapping(value = "/403")
    public String accessDeniedPage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "403";
    }

    @GetMapping(value = "/signup")
    public String signUp (Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "signup";
    }

    @PostMapping (value = "/tosignup")
    public String toSignUp (@RequestParam(name = "user_email") String email,
                            @RequestParam(name = "user_password") String password,
                            @RequestParam(name = "user_re_password") String rePassword,
                            @RequestParam(name = "user_full_name") String fullName) {
        if (password.equals(rePassword)) {
            AuthUser checkUser = userService.getUserByEmail(email);
            if (checkUser == null) {
                AuthUser user = new AuthUser();
                user.setEmail(email);
                user.setFullName(fullName);
                user.setPassword(passwordEncoder.encode(password));
                userService.createUser(user);
                return "redirect:/signup?success";

            }
            return "redirect:/signup?emailerror";
        }
        return "redirect:/signup?passworderror";

    }

    @PostMapping (value = "/updatepassword")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(@RequestParam(name = "old_password") String oldPassword,
                            @RequestParam(name = "new_password") String newPassword,
                            @RequestParam(name = "new_re_password") String reNewPassword) {

            if (newPassword.equals(reNewPassword)) {
            AuthUser currentUser = getCurrentUser();
            AuthUser checkUser = userService.getUserByEmail(currentUser.getEmail());
            if (passwordEncoder.matches(oldPassword, checkUser.getPassword())) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                userService.updateUser(currentUser);
                return "redirect:/profile?success";
            }
            return "redirect:/profile?oldpassworderror";
        }
        return "redirect:/profile?passworderror";

    }

}
