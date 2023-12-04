package com.board.bulletinboardproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
//    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
//    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
