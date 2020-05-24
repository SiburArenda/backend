package com.publicvm.siburarenda.rest;

import com.publicvm.siburarenda.model.Role;
import com.publicvm.siburarenda.model.Status;
import com.publicvm.siburarenda.model.User;
import com.publicvm.siburarenda.security.jwt.JwtAuthenticationException;
import com.publicvm.siburarenda.security.jwt.JwtTokenProvider;
import com.publicvm.siburarenda.service.UserService;
import com.publicvm.siburarenda.dto.AuthenticationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for authentication requests (login, logout, register, etc.)
 *
 * @author Valera
 * @version 1.0
 */

@RestController
@Slf4j
@RequestMapping(value = "/api/")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("public/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                log.warn("In authRestController login user " + requestDto.getUsername() + " wasn't found");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User with username " + username + " wasn't found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles().stream().collect(Collectors.toList()));

            Map<Object, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("username", username);
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("roles", user.getRoles().stream().map(Role::toString).collect(Collectors.toList()));
            response.put("company", user.getCompany());
            response.put("token", token);
            log.info("In authRestController login user " + requestDto.getUsername() + " was found");
            return ResponseEntity.ok(response);
        } catch (DisabledException ex) {
            log.warn("In authRestController login user " + requestDto.getUsername() + " should be ACTIVE");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Activate your account first");
        } catch (BadCredentialsException e) {
            log.warn("In authRestController login user " + requestDto.getUsername() + " invalid un/pass");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or password");
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("In authRestController login user " + requestDto.getUsername() + " unknownEx" + e.getStackTrace());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Whooops, something went wrong");
        }
    }

    @GetMapping("public/token")
    public ResponseEntity<String> tokenValidate(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(jwtTokenProvider.validateToken(token.substring(7)));
    }
}
