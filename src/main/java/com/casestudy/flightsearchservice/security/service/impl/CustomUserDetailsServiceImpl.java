package com.casestudy.flightsearchservice.security.service.impl;

import com.casestudy.flightsearchservice.model.entity.User;
import com.casestudy.flightsearchservice.security.CustomUserDetails;
import com.casestudy.flightsearchservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userService.findByEmail(email);
        return new CustomUserDetails(user);
    }
}
