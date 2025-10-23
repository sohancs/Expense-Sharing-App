package org.wt.com.expense_sharing_app.auth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final UserService userService;

    @Value("${jwt.expiration-in-mins}")
    private Long jwtExpirationMins;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws java.io.IOException, ServletException {
      
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        Map<String,Object> attrMap = oAuth2User.getAttributes();

        String userName = (String) attrMap.get("name");

        User user = userService.getUserByName(userName);

        if(user == null) {
            throw new ServletException("User not found.");
        }

        log.info("Authenticated User: ID={}, Name={}, Email={}", user.getUserId(), user.getName(), user.getEmail());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        
        Map<String, Object> claims = Map.of(
            "email", user.getEmail(),
            "roles", user.getRoles(),
            "issuedAt", simpleDateFormat.format(new Date()),
            "expiration", simpleDateFormat.format(new Date(System.currentTimeMillis() + jwtExpirationMins * 60 * 1000))
        );
        
        String token = jwtService.generateToken(user.getName(), claims);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
       
    }

}
