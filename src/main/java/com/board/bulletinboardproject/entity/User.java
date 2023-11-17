package com.board.bulletinboardproject.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    @Size(min=4,max =10,message = "아이디는 4에서 10글자 사이 입니다.")
    @Pattern(regexp = "[a-z0-9]*$",message = "아이디형식이 일치하지 않습니다.")
    private String username;

    @Column(nullable = false)
    @Size(min =8,max =15,message = "비밀번호는 8에서 15글자 사이 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$",message = "비밀번호형식이 일치하지 않습니다.")
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public User(String username, String password,UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role=role;
    }
}

