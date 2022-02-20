package ru.yajaneya.Spring2Geekbrains.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.AppError;
import ru.yajaneya.Spring2Geekbrains.auth.dto.JwtRequest;
import ru.yajaneya.Spring2Geekbrains.auth.dto.JwtResponse;
import ru.yajaneya.Spring2Geekbrains.auth.entities.User;
import ru.yajaneya.Spring2Geekbrains.auth.services.UserService;
import ru.yajaneya.Spring2Geekbrains.auth.utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authorization(@RequestBody JwtRequest authRequest) {
        return createAuthToken(authRequest, "auth");
    }

//    @PostMapping("/reg")
//    public ResponseEntity<?> registration(@RequestBody JwtRequest authRequest) {
//        User user = new User();
//        user.setUsername(authRequest.getUsername());
//        user.setPassword(authRequest.getPassword());
//        userService.save(user); //TODO сделать проверку на наличие такого пользователя в базе и отправку инфы об ошибке
//        return createAuthToken(authRequest, "reg");
//    }


    private ResponseEntity<?> createAuthToken(JwtRequest authRequest, String m) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError
                    (
                            "AUTH_SERVICE_INCORRECT_USERNAME_OR_PASSWORD",
                            "Incorrect username or password"
                    ), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
