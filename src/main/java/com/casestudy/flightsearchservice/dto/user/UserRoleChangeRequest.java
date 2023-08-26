package com.casestudy.flightsearchservice.dto.user;

import com.casestudy.flightsearchservice.model.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleChangeRequest {

    private UUID id;

    private UserRoleEnum userRole;
}
