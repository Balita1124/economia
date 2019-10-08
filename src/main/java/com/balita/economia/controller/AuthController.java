package com.balita.economia.controller;

import com.balita.economia.model.Role;
import com.balita.economia.model.RoleName;
import com.balita.economia.model.User;
import com.balita.economia.playload.LoginRequest;
import com.balita.economia.playload.SignupRequest;
import com.balita.economia.service.RoleService;
import com.balita.economia.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@Controller
public class AuthController {

    public static final Logger logger = Logger.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ModelAttribute("signUpRequest")
    public SignupRequest signupRequest(){
        return new SignupRequest();
    }

    @GetMapping("/login")
    public String login(Model model) {
//        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
//        model.addAttribute("signupRequest", new SignupRequest());
        return "auth/signup";
    }

    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute("signUpRequest") @Valid SignupRequest signUpRequest, BindingResult bindingResult, Model model) {

//        model.addAttribute("signUpRequest", signUpRequest);
        System.out.println("result:" + bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            System.out.println("Verify data");
            return "auth/signup";
        } else {

            if (userService.existsByUsername(signUpRequest.getUsername())) {
                bindingResult.rejectValue("username", null, "There is already an account registered with that username");
                return "auth/signup";
            }

            if (userService.existsByEmail(signUpRequest.getEmail())) {
                bindingResult.rejectValue("email", null, "There is already an account registered with that email");
                return "auth/signup";
            }

            // Creating user's account
            User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                    signUpRequest.getEmail(), signUpRequest.getPassword());

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role userRole = roleService.findByName(RoleName.ROLE_USER).orElse(null);

            if (userRole == null) {
                bindingResult.rejectValue("global", null, "Role not set, Contact administrator");
                return "auth/signup";
            }

            user.setRoles(Collections.singleton(userRole));

            User result = userService.save(user);

            return "redirect:/";
        }
    }
}
