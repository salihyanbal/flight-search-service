package com.casestudy.flightsearchservice.service.impl;

import com.casestudy.flightsearchservice.dto.auth.LoginRequestDto;
import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.dto.user.PasswordChangeRequestDto;
import com.casestudy.flightsearchservice.dto.auth.RegisterRequestDto;
import com.casestudy.flightsearchservice.exception.ServiceException;
import com.casestudy.flightsearchservice.model.entity.User;
import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import com.casestudy.flightsearchservice.model.enums.UserRoleEnum;
import com.casestudy.flightsearchservice.model.service.TokenModel;
import com.casestudy.flightsearchservice.repository.UserRepository;
import com.casestudy.flightsearchservice.service.UserService;
import com.casestudy.flightsearchservice.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenModel register(RegisterRequestDto registerRequestDto) {
        boolean doesUserExist = this.doesExistByEmail(registerRequestDto.getEmail());
        if(doesUserExist){
            throw new ServiceException(ErrorCodeEnum.ALREADY_EXIST, "User already exist with this email!");
        }

        validatePasswordRegex(registerRequestDto.getPassword());
        User userToSave = User.builder()
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(UserRoleEnum.USER)
                .build();
        User createdUser = createUser(userToSave);
        String token = "";
        TokenModel tokenModel = TokenModel.builder()
                .token(token)
                .user(createdUser)
                .build();
        return tokenModel;
    }

    @Override
    public TokenModel login(LoginRequestDto loginRequestDto) {
        User foundUser = findByEmail(loginRequestDto.getEmail());
        validatePassword(loginRequestDto.getPassword(), foundUser.getPassword());
        String token = "";
        TokenModel tokenModel = TokenModel.builder()
                .token(token)
                .user(foundUser)
                .build();
        return tokenModel;
    }


    @Override
    public User changePassword(String email, PasswordChangeRequestDto passwordChangeRequestDto) {
        User foundUser = findByEmail(email);
        validatePassword(passwordChangeRequestDto.getOldPassword(), foundUser.getPassword());
        validatePasswordRegex(passwordChangeRequestDto.getNewPassword());

        foundUser.setPassword(passwordEncoder.encode(passwordChangeRequestDto.getNewPassword()));
        return updateUser(foundUser);
    }

    @Override
    public void delete(UUID id) {
        User foundUser = findById(id);
        userRepository.delete(foundUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ServiceException(ErrorCodeEnum.NOT_FOUND, "User not found by email!")
        );
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorCodeEnum.NOT_FOUND, "User not found by id!")
        );
    }

    @Override
    public Page<User> findAll(PageableRequest pageableRequest) {
        Pageable pageable = PageUtil.from(pageableRequest);
        return userRepository.findAll(pageable);
    }

    @Override
    public User setUserRole(UUID id, UserRoleEnum role) {
        User foundUser = findById(id);
        foundUser.setRole(role);
        return updateUser(foundUser);
    }

    private void validatePassword(String password, String userPassword) {
        if(!passwordEncoder.matches(password, userPassword)){
            throw new ServiceException(ErrorCodeEnum.SECURITY_BAD_CREDENTIALS, "Password is not correct!");
        }
    }

    private User createUser(User user){
        return userRepository.save(user);
    }

    private User updateUser(User user){
        return userRepository.save(user);
    }

    private boolean doesExistByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    private void validatePasswordRegex(final String password) {
        /* Validation rules
          - Must be at least 6 characters long
          - Must include at least one
            - 1 lowercase alphabetic character
            - 1 uppercase alphabetic character
            - 1 number
            - 1 symbol (special character)
         */
        final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-+_!@#$%^&*.,?])(?=\\S+$).{6,}$";

        if (!password.matches(passwordRegex)) {
            throw new ServiceException(ErrorCodeEnum.VALIDATION, "Password not strong enough!");
        }
    }
}
