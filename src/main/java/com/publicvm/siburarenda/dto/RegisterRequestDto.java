package com.publicvm.siburarenda.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String company;

}
