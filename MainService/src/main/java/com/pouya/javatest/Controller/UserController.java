package com.pouya.javatest.Controller;

import com.pouya.library.Controller.Abstracts.BaseController;
import com.pouya.javatest.Models.User;
import com.pouya.library.PacketTemplate.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        List<User> users = new ArrayList<>();
        users.add(user);
        return ApiResponse.success(user);
    }
}
