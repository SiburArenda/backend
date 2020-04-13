package com.publicvm.siburarenda.service;

import com.publicvm.siburarenda.dto.RegisterRequestDto;
import com.publicvm.siburarenda.model.User;
import org.postgresql.util.PSQLException;

import java.util.List;

/**
 * Service interface for class {@link User}.
 *
 * @author Valera
 * @version 1.0
 */

public interface UserService {

    User register(User user) throws PSQLException;

    User registerManager(User user);

    User activate(User user);

    public User registerDtoToUser(RegisterRequestDto dto);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);
}
