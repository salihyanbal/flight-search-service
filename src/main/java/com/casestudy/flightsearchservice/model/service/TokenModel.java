package com.casestudy.flightsearchservice.model.service;

import com.casestudy.flightsearchservice.model.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenModel {

    private String token;

    private User user;
}
