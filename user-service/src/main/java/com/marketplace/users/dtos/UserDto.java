package com.marketplace.users.dtos;

import com.marketplace.users.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = Constants.INVALID_NAME)
    @Size(min = 3, max = 25, message = Constants.INVALID_NAME_SIZE)
    private String fullName;

    @NotBlank(message = Constants.BLANK_USERNAME)
    @Size(min = 4, max = 25, message = Constants.INVALID_USERNAME_SIZE)
    private String username;

    @NotBlank(message = Constants.BLANK_PASSWORD)
    @Size(min = 5, message = Constants.INVALID_PASSWORD_SIZE)
    private String password;

    @NotBlank(message = Constants.BLANK_EMAIL)
    @Email(message = Constants.INVALID_EMAIL)
    private String email;
    private String phone;
}

