package com.publicvm.siburarenda.service.impl;

import com.publicvm.siburarenda.dto.RegisterRequestDto;
import com.publicvm.siburarenda.repository.RoleRepository;
import com.publicvm.siburarenda.repository.UserRepository;
import com.publicvm.siburarenda.service.EmailService;
import com.publicvm.siburarenda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import com.publicvm.siburarenda.model.Role;
import com.publicvm.siburarenda.model.Status;
import com.publicvm.siburarenda.model.User;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

/**
 * Implementation of {@link UserService} interface.
 * Wrapper for {@link UserRepository} + business logic.
 *
 * @author Valera
 * @version 1.0
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) throws PSQLException {
        Role roleUser = roleRepository.findByName("USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        //TODO(email activation);
        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    public User registerDtoToUser(RegisterRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setCompany(dto.getCompany());
        user.setPassword(dto.getPassword());
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        user.setUpdated(new Timestamp(System.currentTimeMillis()));
        return user;
    }

    @Override
    public User registerManager(User user) {
        Role roleUser = roleRepository.findByName("MANAGER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public User activate(String username, String token) throws BadCredentialsException{
        User user = userRepository.findByUsername(username);
        if (user.getId() == Integer.parseInt(token) && user.getUsername().equals(username)) {
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result.getUsername(), id);
        return result;
    }



    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.setUserInfoById(user.getUsername(), user.getStatus(), user.getCompany(),
                user.getEmail(), user.getFirstName(), user.getLastName(), user.getId());
    }
}
