package kz.aigulsharip.springboot.security.controller;

import kz.aigulsharip.springboot.security.model.AuthUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.extras.springsecurity5.auth.Authorization;

public class BaseController {

    protected AuthUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            AuthUser currentUser = (AuthUser) authentication.getPrincipal();
            return currentUser;
        }
        return null;
    }
}
