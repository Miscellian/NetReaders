package com.netreaders.controller;

import com.netreaders.dto.JwtResponse;
import com.netreaders.dto.LoginForm;
import com.netreaders.dto.SignUpForm;
import com.netreaders.models.RegistrationToken;
import com.netreaders.models.User;
import com.netreaders.security.JwtProvider;
import com.netreaders.service.EmailService;
import com.netreaders.service.RegistrationTokenService;
import com.netreaders.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final RegistrationTokenService registrationTokenService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpForm) {
        if (userService.userExists(signUpForm.getUsername())) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        User user = userService.registerUser(signUpForm);
        emailService.sendEmail(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        if (userService.userExists(loginForm.getUsername())) {
            if (registrationTokenService.tokenExistsByUser(loginForm.getUsername())) {
                return ResponseEntity.badRequest().body("Finish your registration first");
            }
        } else {
            return ResponseEntity.badRequest().body("User Already exists");
        }
        JwtResponse response = userService.login(loginForm);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/confirmRegistration")
    public ResponseEntity<?> confirmRegistration(@RequestParam(name = "token") String token) {

        if (registrationTokenService.tokenIsValid(token)) {
            registrationTokenService.removeToken(token);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createAdmin")
    public boolean createAdmin(@RequestBody SignUpForm signUpForm) {
    	if (userService.userExists(signUpForm.getUsername())) {
            return false;
        }
        userService.registerPriviledgedUser(signUpForm, new String[]{"ADMIN"});

        return true;
    }

    @PostMapping("/createModerator")
    public boolean createModerator(@RequestBody SignUpForm signUpForm,
                                   @RequestBody String[] roles) {
        if (userService.userExists(signUpForm.getUsername())) {
            return false;
        }
        userService.registerPriviledgedUser(signUpForm, roles);

        return true;
    }
    
    @GetMapping("/{username}")
    public User getByUsername(@PathVariable String username) {
            return userService.findByUsername(username);
    }

}
