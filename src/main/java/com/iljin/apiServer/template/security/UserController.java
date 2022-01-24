package com.iljin.apiServer.template.security;

import com.iljin.apiServer.core.security.user.User;
import com.iljin.apiServer.core.security.user.UserDto;
import com.iljin.apiServer.core.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/id/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @GetMapping("/login-id/{loginId}")
    public Optional<User> getUserByLoginId(@PathVariable String loginId) {
        return userService.getUserByLoginId(loginId);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody UserDto dto) {
        return userService.addUser(dto);
    }

    @DeleteMapping("/{loginId}")
    public ResponseEntity<String> deleteUser(@PathVariable("loginId") String loginId) {
        return userService.deleteUser(loginId);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }
}
