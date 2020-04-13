package com.publicvm.siburarenda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.publicvm.siburarenda.model.Event;
import com.publicvm.siburarenda.model.Role;
import com.publicvm.siburarenda.model.Status;
import com.publicvm.siburarenda.model.User;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * DTO class for user requests by ROLE_ADMIN
 *
 * @author Valera
 * @version 1.0
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private List<Role> roles;
    //private List<Event> events;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));
     //   user.setRoles(roles);
        //user.setEvents(events);
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getUsername());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setStatus(user.getStatus().name());
        adminUserDto.setRoles(user.getRoles());
        //adminUserDto.setEvents(user.getEvents());
        return adminUserDto;
    }
}
