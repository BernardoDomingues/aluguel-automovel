package com.aluguel.config;

import com.aluguel.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String requestPath = request.getRequestURI();
        String method = request.getMethod();
        
        if (isPublicRoute(requestPath, method)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwt = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                logger.error("JWT token is invalid: " + e.getMessage());
            }
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = usuarioService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(jwt, username)) {
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: " + e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    private boolean isPublicRoute(String path, String method) {
        // Rotas de autenticação
        if (path.startsWith("/api/auth/")) {
            return true;
        }
        
        // Rotas de criação (POST)
        if ("POST".equals(method)) {
            if (path.equals("/api/usuarios") || 
                path.equals("/api/clientes") || 
                path.equals("/api/agentes")) {
                return true;
            }
        }
        
        // Rotas do Swagger
        if (path.startsWith("/swagger-ui/") || 
            path.startsWith("/v3/api-docs/") || 
            path.startsWith("/api-docs/") ||
            path.startsWith("/swagger-resources/") ||
            path.startsWith("/webjars/") ||
            path.equals("/swagger-ui.html") ||
            path.equals("/swagger-ui/index.html")) {
            return true;
        }
        
        // Console H2
        if (path.startsWith("/h2-console/")) {
            return true;
        }
        
        return false;
    }
}
