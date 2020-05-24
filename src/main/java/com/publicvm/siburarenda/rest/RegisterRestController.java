package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.dto.RegisterRequestDto;
import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.service.EmailService;
import com.publicvm.siburarenda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/api/")
public class RegisterRestController {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public RegisterRestController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping(value = "public/register")
    public ResponseEntity registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.registerDtoToUser(registerRequestDto);
        log.info("In RegisterRestController registerUser " + registerRequestDto.getUsername() + " was registered");
        return requestRunner(user, false);
    }

    @PostMapping(value = "admin/register")
    public ResponseEntity registerManager(@RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.registerDtoToUser(registerRequestDto);
        log.info("In RegisterRestController registerManager " + registerRequestDto.getUsername() + " was registered");
        return requestRunner(user, true);
    }

    @PostMapping(value = "public/activate")
    public ResponseEntity activate(@RequestParam String username, @RequestParam String token) {
        try {
            userService.activate(username, token);
            log.info("In RegisterRestController activate " + username + " was activated");
            return ResponseEntity.ok("User: " + username + " was activated");
        } catch (BadCredentialsException ex) {
            log.warn("In RegitsterRestController activate BadCredEx " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    private ResponseEntity requestRunner(User user, boolean isManager) {
        try {
            if (!isManager) {
                User registeredUser = userService.register(user);
                StringBuilder message = new StringBuilder();
                message.append("You should follow this link in order to activate our account on SiburArenda \n");
                message.append("http://siburarenda.publicvm.com/api/public/activate?username=" + user.getUsername() +
                        "&?token=" + registeredUser.getId());
                log.info("email to " + user.getEmail() + " was sended");
                //emailService.send(message.toString(), user.getEmail());

            } else
                userService.registerManager(user);
        } catch (DataIntegrityViolationException ex) {
            log.warn("In RegisterRestController reqRunner " + user.getUsername() + " already exists");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User with such email or username already exists");
        } catch (PSQLException ex) {
            log.error("In RegisterRestController reqRunner " + user.getUsername() + " ERROR " + ex.getStackTrace());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Whoooops, something went wrong");
        }
        return ResponseEntity.ok("User with username: " + user.getUsername() + " was registred.");
    }

}
