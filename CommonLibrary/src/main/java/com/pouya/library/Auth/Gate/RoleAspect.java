package com.pouya.library.Auth.Gate;

import com.pouya.library.DTO.UserInfo;
import com.pouya.library.Enums.UserRoles;
import com.pouya.library.Execptions.AuthException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class RoleAspect {

    @Before("@annotation(hasRole) || @within(hasRole)")
    public void checkRole(JoinPoint joinPoint, HasRole hasRole) {

        //age @annonation ro method nabod az user begir (parent)
        if (hasRole == null) {
            Class<?> targetClass = joinPoint.getTarget().getClass();
            hasRole = targetClass.getAnnotation(HasRole.class);
        }
        if (hasRole == null) return;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) {
            throw new AuthException("Unauthorized");
        }
        UserRoles requiredRole = hasRole.role();
        UserInfo _user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean has = _user.getRoles().contains(requiredRole.name());

        if (!has) {
            throw new AuthException("role_error");
        }
    }
}