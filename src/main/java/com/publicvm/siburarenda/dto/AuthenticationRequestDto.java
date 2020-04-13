package com.publicvm.siburarenda.dto;

import lombok.Data;

/**
 * DTO class for authentication (login) request.
 *
 * @author Valera
 * @version 1.0
 */

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
