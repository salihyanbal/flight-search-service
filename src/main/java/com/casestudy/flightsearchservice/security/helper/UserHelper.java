package com.casestudy.flightsearchservice.security.helper;

import com.casestudy.flightsearchservice.model.entity.User;
import com.casestudy.flightsearchservice.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {

    public static User getLoggerUser(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return ((CustomUserDetails) authentication.getDetails()).getUser();
        } else {
            return null;
        }
    }
}
