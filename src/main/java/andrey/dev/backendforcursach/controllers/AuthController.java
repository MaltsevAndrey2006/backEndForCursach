package andrey.dev.backendforcursach.controllers;

import andrey.dev.backendforcursach.dto.JwtAuthenticationDto;
import andrey.dev.backendforcursach.dto.UserCredentialsDto;
import andrey.dev.backendforcursach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signIn")
    public JwtAuthenticationDto signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            return userService.signIn(userCredentialsDto);
        } catch (RuntimeException e1) {
            throw new RuntimeException(e1);
        }
    }

}
