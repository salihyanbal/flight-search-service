package com.casestudy.flightsearchservice.controller;

import com.casestudy.flightsearchservice.dto.auth.LoginRequestDto;
import com.casestudy.flightsearchservice.dto.auth.RegisterRequestDto;
import com.casestudy.flightsearchservice.dto.auth.TokenResponseDto;
import com.casestudy.flightsearchservice.dto.user.PasswordChangeRequestDto;
import com.casestudy.flightsearchservice.dto.user.UserResponseDto;
import com.casestudy.flightsearchservice.model.entity.User;
import com.casestudy.flightsearchservice.model.service.TokenModel;
import com.casestudy.flightsearchservice.security.helper.UserHelper;
import com.casestudy.flightsearchservice.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth")
public class AuthController extends AbstractController {

    private final UserService userService;

    @Autowired
    public AuthController(ModelMapper modelMapper, UserService userService) {
        super(modelMapper);
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto){
        TokenModel tokenModel = userService.register(registerRequestDto);
        return ResponseEntity.ok(
                getModelMapper().map(tokenModel, TokenResponseDto.class)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        TokenModel tokenModel = userService.login(loginRequestDto);
        return ResponseEntity.ok(
                getModelMapper().map(tokenModel, TokenResponseDto.class)
        );
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> changePassword(@Valid @RequestBody PasswordChangeRequestDto passwordChangeRequestDto){
        User userToUpdate = UserHelper.getLoggerUser();
        User updatedUser = userService.changePassword(userToUpdate.getEmail(), passwordChangeRequestDto);
        return ResponseEntity.ok(
                getModelMapper().map(updatedUser, UserResponseDto.class)
        );
    }
}
