package com.casestudy.flightsearchservice.security;

import com.casestudy.flightsearchservice.exception.CustomAuthenticationException;
import com.casestudy.flightsearchservice.model.entity.User;
import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import com.casestudy.flightsearchservice.security.helper.TokenHelper;
import com.casestudy.flightsearchservice.service.UserService;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final TokenHelper tokenHelper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String email;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        token = tokenHelper.getTokenFromHeader(authHeader);
        if (StringUtils.isNotEmpty(token)) {
            try {
                email = tokenHelper.extractEmail(token);
            } catch (final JwtException jwtException) {
                throw new CustomAuthenticationException(ErrorCodeEnum.SECURITY_USER_AUTHORIZATION_FAIL);
            }

            final User user = userService.findByEmail(email);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            if(tokenHelper.validateToken(token, userDetails)){
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword(),
                        userDetails.getAuthorities()
                );
                authentication.setDetails(userDetails); // TODO: CHECK
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
