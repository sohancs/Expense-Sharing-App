package org.wt.com.expense_sharing_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wt.com.expense_sharing_app.dto.UserDTO;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.persistence.repository.UserRepository;
import org.wt.com.expense_sharing_app.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = objectMapper.convertValue(userDTO, User.class);
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
