package com.marketplace.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fullName;

    @NotBlank
    @Size(min = 4, max = 20)
    @Column(name = "user_name")
    private String userName;

    @NotBlank
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private ROLE role;
}
