package andrey.dev.backendforcursach.controllers;

import andrey.dev.backendforcursach.dto.UserDto;
import andrey.dev.backendforcursach.dto.UserRequest;
import andrey.dev.backendforcursach.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
