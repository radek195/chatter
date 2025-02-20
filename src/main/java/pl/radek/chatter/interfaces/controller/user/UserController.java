package pl.radek.chatter.interfaces.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/user/{id}/preference")
    public void addUserPreferences(@PathVariable Long id, @Valid @RequestBody UserPreferenceRequest request) {
        userService.addUserPreferenceForUser(request.toDto(), id);
    }

}
