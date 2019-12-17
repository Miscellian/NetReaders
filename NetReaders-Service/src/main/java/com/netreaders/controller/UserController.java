package com.netreaders.controller;

import com.netreaders.dto.*;
import com.netreaders.models.Role;
import com.netreaders.models.User;
import com.netreaders.service.EmailService;
import com.netreaders.service.RegistrationTokenService;
import com.netreaders.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@Log4j
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

    @PostMapping("/editUser")
    public ResponseEntity<?> editUser(@RequestBody EditUserForm editUserForm) {
        userService.editUser(editUserForm);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        if (userService.userExists(loginForm.getUsername())) {
            if (registrationTokenService.tokenExistsByUser(loginForm.getUsername())) {
                return ResponseEntity.badRequest().body("Finish your registration first");
            }
        } else {
            return ResponseEntity.badRequest().body("User doesn't exists");
        }
        if (!userService.checkCredentials(loginForm)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
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
        User user = userService.registerPriviledgedUser(signUpForm, new String[]{"ADMIN"});
        emailService.sendEmail(user);
        return true;
    }

    @PostMapping("/removeAdmin")
    public void removeAdmin(@RequestBody User user) {
        userService.removeUser(user.getUsername());
    }

    @PostMapping("/createModerator")
    public boolean createModerator(@RequestBody ModeratorForm moderatorForm) {
        if (userService.userExists(moderatorForm.getUser().getUsername())) {
            return false;
        }
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setEmail(moderatorForm.getUser().getEmail());
        signUpForm.setFirstName(moderatorForm.getUser().getFirstName());
        signUpForm.setLastName(moderatorForm.getUser().getLastName());
        signUpForm.setUsername(moderatorForm.getUser().getUsername());
        signUpForm.setUserPassword(moderatorForm.getUser().getUserPassword());
        User user = userService.registerPriviledgedUser(signUpForm, moderatorForm.getRoles());
        
        emailService.sendEmail(user);
        
        return true;
    }

    @PostMapping("/updateModeratorRoles")
    public void updateModeratorRoles(@RequestBody ModeratorForm moderatorForm) {
        userService.updateModeratorRoles(moderatorForm.getUser().getUsername(), moderatorForm.getRoles());
    }

    @PostMapping("/removeModerator")
    public void removeModerator(@RequestBody User user) {
        userService.removeUser(user.getUsername());
    }

    @GetMapping("/getAdminsList")
    public Collection<User> getAdminsList() {
        return userService.getAdminsList();
    }

    @GetMapping("/getModeratorsList")
    public Collection<User> getModeratorsList() {
        return userService.getModeratorsList();
    }

    @GetMapping("/{username}")
    public User getByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/checkIfUsernameExists")
    public boolean checkIfUsernameExists(@RequestParam(name = "username") String username) {
        return userService.checkIfUsernameExists(username);
    }

    @GetMapping("/checkIfEmailExists")
    public boolean checkIfEmailExists(@RequestParam(name = "email") String email) {
        return userService.checkIfEmailExists(email);
    }

    @GetMapping("/getRolesForModerator")
    public Collection<Role> getRolesForModerator(@RequestParam(name = "moderatorUsername") String moderatorUsername) {
        return userService.getRolesForModerator(moderatorUsername);
    }
}
