package pl.radek.chatter.interfaces.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.radek.chatter.domain.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public Long createGuestUser(@Valid @RequestBody UserRequest request) {
        return userService.createGuestUser(request.toUserDto());
    }

}
