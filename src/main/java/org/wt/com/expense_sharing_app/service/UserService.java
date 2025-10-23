package org.wt.com.expense_sharing_app.service;

import java.util.List;

import org.wt.com.expense_sharing_app.dto.UserDTO;
import org.wt.com.expense_sharing_app.persistence.entity.User;

public interface UserService {

    public User createUser(UserDTO userDTO);
    public User getUserById(Long userId);
    public List<User> getAllUsers();
    public User getUserByName(String userName);
}
