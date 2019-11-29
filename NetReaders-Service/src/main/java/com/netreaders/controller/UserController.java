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
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpForm) {
        User user = userService.findByNickname(signUpForm.getUser_name());
        if (user != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        user = userService.registerUser(signUpForm);
        RegistrationToken token = registrationTokenService.createToken(user);
        emailService.sendEmail(user, token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        User user = userService.findByNickname(loginForm.getUsername());
        if (user != null) {
            if (registrationTokenService.getTokenByUser(user) != null) {
                return ResponseEntity.badRequest().body("Finish your registration first");
            }
        } else {
            return ResponseEntity.badRequest().body("User Already exists");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @GetMapping("/confirmRegistration")
    public ResponseEntity<?> confirmRegistration(@RequestParam(name = "token") String token) throws SQLException {

        if (registrationTokenService.tokenIsValid(token)) {
            registrationTokenService.removeToken(token);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createAdmin")
    public boolean createAdmin(@RequestBody SignUpForm signUpForm) {
        User user = userService.findByNickname(signUpForm.getUser_name());
        if (user != null) {
            return false;
        }
        user = userService.registerPriviledgedUser(signUpForm, new String[]{"ADMIN"});

        return true;
    }

    @PostMapping("/createModerator")
    public boolean createModerator(@RequestBody SignUpForm signUpForm,
                                   @RequestBody String[] roles) {
        User user = userService.findByNickname(signUpForm.getUser_name());
        if (user != null) {
            return false;
        }
        user = userService.registerPriviledgedUser(signUpForm, roles);

        return true;
    }

}
