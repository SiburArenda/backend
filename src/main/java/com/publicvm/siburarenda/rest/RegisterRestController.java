package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.RegisterRequestDto;
import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.service.UserService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/")
public class RegisterRestController {

    private final UserService userService;

    @Autowired
    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "public/register")
    public ResponseEntity registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.registerDtoToUser(registerRequestDto);
        return requestRunner(user, false);
    }

    @PostMapping(value = "admin/register")
    public ResponseEntity registerManager(@RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.registerDtoToUser(registerRequestDto);
        return requestRunner(user, true);
    }

    private ResponseEntity requestRunner(User user, boolean isManager) {
        try {
            if(!isManager)
                userService.register(user);
            else
                userService.registerManager(user);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User with such email or username already exists");
        } catch (PSQLException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Whoooops, something went wrong");
        }
        return ResponseEntity.ok("User with username: " + user.getUsername() + " was registred.");
    }

}
