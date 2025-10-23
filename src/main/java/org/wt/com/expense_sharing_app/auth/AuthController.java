package org.wt.com.expense_sharing_app.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {


    @GetMapping("/me")
    public String me(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("OAuth2User: {}", oAuth2User.getAttributes());
        return oAuth2User.getAttribute("name");
    }
    
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws Exception {
        response.sendRedirect("/oauth2/authorization/google");
    } 

    @GetMapping("/failure")
    public String loginFailure() {
        return "Google login failed. Please try again.";
    }
    
}
