package com.bufkes.service.recipe.service.impl;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bufkes.service.recipe.model.User;
import com.bufkes.service.recipe.repository.UserRepository;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
    }

    public User createUser(User user) {
        User existingUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
        Assert.isTrue(existingUser == null, ErrorType.DATA_VALIDATION, "Username already exists");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        Assert.isTrue(user != null, ErrorType.DATA_VALIDATION, "User does not exist");
        userRepository.delete(user);
    }

    public User updatePassword(User user) {
        User existingUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
        Assert.isTrue(existingUser != null, ErrorType.DATA_VALIDATION, "User does not exist");
        existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(existingUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
