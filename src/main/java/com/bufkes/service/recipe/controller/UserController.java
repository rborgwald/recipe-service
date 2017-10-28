package com.bufkes.service.recipe.controller;

import com.bufkes.service.recipe.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bufkes.service.recipe.model.User;
import com.bufkes.service.recipe.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("${gateway.api.prefix}/users")
public class UserController {

    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping()
    public List<User> getUsers() {
        return userDetailsServiceImpl.getUsers();
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        return userDetailsServiceImpl.createUser(user);
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam String username) {
        userDetailsServiceImpl.deleteUser(username);
    }

    @PutMapping()
    public User updatePassword(@RequestBody User user) {
        return userDetailsServiceImpl.updatePassword(user);
    }
}
