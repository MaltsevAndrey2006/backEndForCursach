package com.dev.backendforcursach.controller;

import com.dev.backendforcursach.model.dto.UserDto;
import com.dev.backendforcursach.model.dto.UserRequest;
import com.dev.backendforcursach.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "Контроллер для управления пользователями")
public class UserController {
  private final UserService userService;

  @PostMapping("registration")
  public void createUser(@RequestBody UserRequest userRequest) {
    userService.createUser(userRequest);
  }

  @GetMapping("login/{login}")
  public ResponseEntity<UserDto> findUserByLogin(@PathVariable String login) {
    return ResponseEntity.ok(userService.findByLogin(login));
  }

  @GetMapping("{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @DeleteMapping("{id}")
  public void deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
  }

  @PatchMapping("{id}")
  public void updateUserById(@PathVariable Long id, @RequestParam BigDecimal balance, @RequestParam String address) {
    userService.updateUser(balance, address, id);
  }

  @PostMapping
  public void createAdmin(@RequestBody UserRequest userRequest) {
    userService.createAdmin(userRequest);
  }

  @GetMapping("admins")
  public ResponseEntity<List<UserDto>> getAllAdmins() {
    return ResponseEntity.ok(userService.getAllAdmins());
  }

  @GetMapping("others")
  public ResponseEntity<List<UserDto>> getAllUsersExceptAdmin(@RequestParam Long id) {
    return ResponseEntity.ok(userService.getAllUsersExceptAdmin(id));
  }
}
