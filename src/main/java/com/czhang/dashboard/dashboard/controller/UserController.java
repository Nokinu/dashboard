package com.czhang.dashboard.dashboard.controller;

import com.czhang.dashboard.dashboard.service.UserService;
import lombok.extern.java.Log;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@Log
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        log.info("Login info page is called");
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        try{
            currentUser.login(usernamePasswordToken);
            if(currentUser.isAuthenticated()) {
                log.info("Login Successfully!");
                return "redirect:/comments/all";
            }
        } catch (AuthenticationException e) {
            log.warning(e.getMessage());
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/doRegister")
    public String doRegister(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        if(userService.registerNewUser(username, password)) {
            return "login";
        }
        return "register";
    }

    @PostMapping("/doLogout")
    public String doLogout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }

}
