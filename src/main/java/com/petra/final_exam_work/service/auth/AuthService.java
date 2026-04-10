package com.petra.final_exam_work.service.auth;

import com.petra.final_exam_work.dto.requestDto.LoginRequest;
import com.petra.final_exam_work.dto.responseDto.LoginResponse;
import org.springframework.http.ResponseCookie;
import com.petra.final_exam_work.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generate JWT
        String token = jwtService.generateToken(userDetails);

        // Create HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false) // true if using https
                .path("/")
                .maxAge(60 * 60)
                .sameSite("Lax") //explicitly set SameSite
                .build();

        //SameSite("strict"); for production only
        response.addHeader("Set-Cookie", cookie.toString());


        //extract roles
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        System.out.println("Authorities at login: " + roles);
        return new LoginResponse(roles);
    }

    public void logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // delete cookie immediately
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

    }
}
