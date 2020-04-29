package com.publicvm.siburarenda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.publicvm.siburarenda.model.Role;
import com.publicvm.siburarenda.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO class for user requests by ROLE_USER
 *
 * @author Valera
 * @version 1.0
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(Role::toString).collect(Collectors.toList()));

        return userDto;
    }
}
