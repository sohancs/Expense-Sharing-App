package org.wt.com.expense_sharing_app.auth;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Value("${admin.email}")
    private List<String> adminEmailIds;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Oauth2UserService loadUser called : {}", userRequest);
         OAuth2User oAuth2User = super.loadUser(userRequest);

         Map<String, Object> attrMap = oAuth2User.getAttributes();

         String googleId = (String) attrMap.get("sub");
         String email = (String) attrMap.get("email"); 
         String userName = (String) attrMap.get("name"); 

         userRepository.findByName(userName).orElseGet(() -> {
            var user = new User();
            user.setName(userName);
            user.setEmail(email);
            user.setGoogleId(String.valueOf(googleId));

            if(adminEmailIds.contains(email))
                user.getRoles().add("ROLE_ADMIN");

            else
                user.getRoles().add("ROLE_USER");

            return userRepository.save(user);
         });

         return oAuth2User;
    }
}
