package com.marketplace.users.web;

import com.marketplace.users.domain.User;
import com.marketplace.users.services.UserService;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "api/v1/user/")
@RestController
public class Controller {

    private final UserService userService;

    @PostMapping(value = "register")
    public ResponseEntity addUser(@RequestBody UserDto userDto) {
        User user = userService.addUser(userDto);

        return ResponseEntity.created(URI.create("http://localhost:8080/" + user.getUserName()))
                .build();
    }

    @GetMapping(value = "{userName}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userName) {
        UserDto user = userService.getUserByUserName(userName);

        return ResponseEntity.ok(user);
    }
}
