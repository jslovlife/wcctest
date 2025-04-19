package com.wcc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wcc.demo.model.entity.User;
import com.wcc.demo.repository.UserRepository;

@Service    
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean validateUser(String username, String password) {

        User user = userRepository.findByUsernamePassword(username, password).orElse(null);

        if (user == null) {
            return false;
        }

        return true;
    }
}
