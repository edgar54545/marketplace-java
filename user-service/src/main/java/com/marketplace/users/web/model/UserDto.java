package com.marketplace.users.web.model;

import com.marketplace.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = "Invalid Name.")
    @Size(min = 3, max = 25, message = "Name require more than 3 and less than 25 characters")
    private String fullName;

    @NotBlank(message = "Invalid username")
    @Size(min = 4, max = 25, message = "Username require more than 4 and less than 25 characters")
    private String username;

    @NotBlank(message = "Password can't be blank")
    @Size(message = "Minimum 5 characters required for password", min = 5)
    private String password;

    @Email(message = "Invalid e-mail")
    private String email;
    private String phone;

    public static User toUser(UserDto userDto) {
        return User.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .build();
    }

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .build();
    }
}

