package com.marketplace.users.web;

import com.marketplace.users.services.UserService;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "api/v1/users")
@RestController
public class Controller {
    private final UserService userService;

    @PostMapping(value = {"/sign-up"})
    public ResponseEntity save(@RequestBody @Valid UserDto userDto) {
        String createdUserUsername = userService.addUser(userDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userName}")
                .buildAndExpand(createdUserUsername)
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        UserDto user = userService.getUserByUserName(username);

        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/{username}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String username, @RequestBody @Valid UserDto userDto) {
        userService.update(username, userDto);
    }
}
