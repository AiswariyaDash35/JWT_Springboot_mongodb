package com.practice.demo.config;

import com.practice.demo.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtValidator extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;

    public JwtValidator(CustomUserDetailsService userDetailsService, JwtProvider jwtProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(JwtConstants.HEADER_STRING);

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            jwtToken = authorizationHeader.substring(JwtConstants.TOKEN_PREFIX.length());
            username=jwtProvider.extractEmail(jwtToken);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);

            if(jwtProvider.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
