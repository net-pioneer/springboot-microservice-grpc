package com.pouya.library.Controller.Abstracts;


import com.pouya.library.DTO.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    protected UserInfo getUser(){
       // Object principal2 = SecurityContextHolder.getContext().getAuthentication().getCredentials(); TOKEN
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserInfo userInfo) {
            return userInfo;
        }
        return null;
    }
}
