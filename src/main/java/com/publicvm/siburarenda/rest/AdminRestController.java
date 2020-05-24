package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.service.UserService;
import com.publicvm.siburarenda.dto.AdminUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for ROLE_ADMIN requests.
 *
 * @author Valera
 * @version 1.0
 */

@RestController
@Slf4j
@RequestMapping(value = "/api/admin/")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            log.warn("In adminRestController getUserById user with id " + id + " wasn't found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);
        log.warn("In adminRestController getUserById user with id " + id + " was found");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @GetMapping(value = "users")
    public ResponseEntity<List<AdminUserDto>> getUsers() {
        List<User> users = userService.getAll();
        List<AdminUserDto> adminUserDtos;
        adminUserDtos = users.stream().map(AdminUserDto::fromUser).collect(Collectors.toList());
        log.warn("In adminRestController getUsers was invoked");
        return new ResponseEntity<>(adminUserDtos, HttpStatus.OK);
    }
}
