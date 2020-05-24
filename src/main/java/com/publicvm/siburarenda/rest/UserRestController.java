package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.UserDto;
import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    UserService userService;

    @PostMapping("/profile/update")
    public ResponseEntity<UserDto> modify(@RequestBody UserDto userDto) {
        log.info("IN UserRestController modify user: " + userDto.getUsername() + "was modified");
        userService.update(UserDto.toUser(userDto));
        return ResponseEntity.ok(userDto);
    }

}
