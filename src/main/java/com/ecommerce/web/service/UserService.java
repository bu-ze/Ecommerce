package com.ecommerce.web.service;

import com.ecommerce.web.model.User;
import com.ecommerce.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void regsiterUser(User user){
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalArgumentException("username exist");
        }
        userRepository.save(user);
    }

    public User loginUser(String username, String password){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            return optionalUser.get();
        }
        throw new IllegalArgumentException("invalid username or password");
    }
}
