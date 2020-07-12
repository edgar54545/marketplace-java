package com.marketplace.users.web.model;

import com.marketplace.users.domain.ROLE;
import com.marketplace.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String phone;

    public static User toUser(UserDto userDto) {
        return User.builder()
                .fullName(userDto.getFullName())
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .build();
    }

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .fullName(user.getFullName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}

