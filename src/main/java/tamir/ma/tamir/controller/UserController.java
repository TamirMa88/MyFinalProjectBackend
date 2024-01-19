package tamir.ma.tamir.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tamir.ma.tamir.dto.AuthDTO;
import tamir.ma.tamir.dto.Token;
import tamir.ma.tamir.entity.User;
import tamir.ma.tamir.service.UserService;

@RestController()
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody AuthDTO authDTO) {
        User user = userService.register(authDTO);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody AuthDTO authDTO) {
        Token token = userService.signIn(authDTO);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> me() {
        String email = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

}
