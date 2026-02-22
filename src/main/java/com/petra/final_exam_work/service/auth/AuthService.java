package com.petra.final_exam_work.service.auth;

import com.petra.final_exam_work.dto.requestDto.LoginRequest;
import com.petra.final_exam_work.dto.responseDto.LoginResponse;
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
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true if using https
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 h expire time
        //cookie.setSameSite("strict"); for production only

        response.addCookie(cookie);

        //extract roles
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        System.out.println("Authorities at login: " + roles);
        return new LoginResponse(roles);
    }

    public void logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //true in production https
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete cookie immediately

        response.addCookie(cookie);
    }
}
