package com.casestudy.flightsearchservice.dto.auth;

import com.casestudy.flightsearchservice.dto.user.UserResponseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDto {

    private String token;

    private UserResponseDto user;
}
