package com.example.backend.service;

import com.example.backend.entity.Account;
import com.example.backend.entity.Users;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users createUser(Account account) {
        return userRepository.save(new Users(account));
    }
}
