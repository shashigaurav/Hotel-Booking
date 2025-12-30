package org.hotel.hotelbookingsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hotel.hotelbookingsystem.config.JwtUtil;
import org.hotel.hotelbookingsystem.dto.UserDto;
import org.hotel.hotelbookingsystem.dto.UserReqLogin;
import org.hotel.hotelbookingsystem.dto.UserReqSignUp;
import org.hotel.hotelbookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserReqSignUp userReqSignUp) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(userReqSignUp));
    }

   @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserReqLogin userReqLogin) {

        UserDto userDto = userService.login(userReqLogin.getEmail(),userReqLogin.getPassword());

        String token = jwtUtil.generateToken(userReqLogin.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", userDto);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);

        String email = jwtUtil.extractEmail(token);

        UserDto profile = userService.getProfile(email);
        return ResponseEntity.ok(profile);

    }


}
