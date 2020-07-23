package com.marketplace.users.web;

import com.marketplace.users.domain.User;
import com.marketplace.users.services.UserService;
import com.marketplace.users.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping(value = "api/v1/users")
@RestController
public class Controller {

    private final UserService userService;

    @PostMapping(value = {"/", ""})
    public ResponseEntity save(@RequestBody @Valid UserDto userDto) {
        String createdUserUsername = userService.addUser(userDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userName}")
                .buildAndExpand(createdUserUsername)
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping(value = "/{userName}")
    public ResponseEntity<UserDto> get(@PathVariable String userName) {
        UserDto user = userService.getUserByUserName(userName);

        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/{userName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String userName, @RequestBody @Valid UserDto userDto) {
        userService.update(userName, userDto);
    }
}
