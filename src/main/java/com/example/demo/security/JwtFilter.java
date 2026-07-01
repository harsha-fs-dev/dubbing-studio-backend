package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Read the Authorization header from the incoming request
        String authHeader = request.getHeader("Authorization");

        // Step 2: If header is missing or doesn't start with "Bearer", skip JWT processing
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Step 3: Extract the token by removing the "Bearer " prefix (7 characters)
        String token = authHeader.substring(7);
        // Step 4: Extract the email from the token
        String email = jwtUtil.extractEmail(token);
        // Step 5: If email is found and no authentication is set yet in this request
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Step 6: Load the user from the database using the email
            User user = userRepository.findByEmail(email).orElse(null);

            // Step 7: If user exists and the token is valid, set authentication
            if (user != null && jwtUtil.validateToken(token, email)) {
                // Step 8: Create the authority using the user's role (e.g., "ROLE_ADMIN")
                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                );
                // Step 9: Build the authentication object with user details and role
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                null,
                                authorities
                        );
                // Step 10: Attach request details (IP address, session info) to authentication
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Step 11: Store the authentication in the SecurityContext for this request
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Step 12: Continue to the next filter or the controller
        filterChain.doFilter(request, response);
    }
}