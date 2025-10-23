package org.wt.com.expense_sharing_app.auth;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.wt.com.expense_sharing_app.persistence.entity.User;
import org.wt.com.expense_sharing_app.persistence.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws java.io.IOException, ServletException {
      
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if(StringUtils.isBlank(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String userName;

        try {
            userName = jwtService.extractUserName(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(jwtService.isTokenValid(token, userName)) {
               User user = userRepository.findByName(userName).orElse(null);

               if(user != null) {
                    List<SimpleGrantedAuthority> roles = user.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
                    UsernamePasswordAuthenticationToken authetication =
                            new UsernamePasswordAuthenticationToken(
                                    userName,
                                    null,
                                    roles
                            );
                    authetication.setDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(authetication);
               }
            }
        }

        filterChain.doFilter(request, response);
    }

}
