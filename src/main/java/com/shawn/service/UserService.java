package com.shawn.service;

import com.shawn.model.entity.User;
//import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * @author Xiaoyue Xiao  
 */
// extends UserDetailsService
public interface UserService  {

    Optional<User> getUserById(Long id);

    boolean saveUser(User user);

    boolean modifyUserOnPasswordById(User user);

    boolean deleteUserById(Long id);

}
