package com.wcc.demo.service;

import com.wcc.demo.model.entity.User;

public interface UserService {

    User findByUsername(String username);

    User save(User user);

    User update(User user);

    boolean validateUser(String username, String password);
}
