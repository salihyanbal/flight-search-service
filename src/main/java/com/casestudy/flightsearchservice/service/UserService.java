package com.casestudy.flightsearchservice.service;

import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.dto.user.PasswordChangeRequestDto;
import com.casestudy.flightsearchservice.dto.auth.RegisterRequestDto;
import com.casestudy.flightsearchservice.dto.auth.LoginRequestDto;
import com.casestudy.flightsearchservice.model.entity.User;
import com.casestudy.flightsearchservice.model.enums.UserRoleEnum;
import com.casestudy.flightsearchservice.model.service.TokenModel;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    TokenModel register(RegisterRequestDto registerRequestDto);

    TokenModel login(LoginRequestDto loginRequestDto);

    User changePassword(String email, PasswordChangeRequestDto passwordChangeRequestDto);

    void delete(UUID id);

    User findByEmail(String email);

    User findById(UUID id);

    Page<User> findAll(PageableRequest pageableRequest);

    User setUserRole(UUID id, UserRoleEnum role);
}
