package com.pouya.library.Auth.Gate;

import com.pouya.library.Enums.UserRoles;
import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasRole {
    UserRoles role();
}