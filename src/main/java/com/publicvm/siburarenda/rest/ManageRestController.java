package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.service.UserService;
import com.publicvm.siburarenda.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller user connected requestst.
 *
 * @author Valera
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/manage/")
public class ManageRestController {
    private final UserService userService;

    @Autowired
    public ManageRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
