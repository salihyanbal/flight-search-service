package com.casestudy.flightsearchservice.dto.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequestDto {

    @Size(max = 40)
    private String oldPassword;

    @Size(max = 40)
    private String newPassword;
}
