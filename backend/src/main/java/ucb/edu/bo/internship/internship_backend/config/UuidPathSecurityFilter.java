package ucb.edu.bo.internship.internship_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class UuidPathSecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UuidPathSecurityFilter.class);
    private final SecurityConstraintsProperties securityConstraintsProperties;

    public UuidPathSecurityFilter(SecurityConstraintsProperties securityConstraintsProperties) {
        this.securityConstraintsProperties = securityConstraintsProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String pathInfo = request.getRequestURI();
        String method = request.getMethod();

        logger.info("Path Info: {}, Method: {}", pathInfo, method);

        // Verificar si la ruta es pública
        if (isPublicPath(pathInfo, method)) {
            logger.info("Public path accessed: {}", pathInfo);
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar si la ruta protegida no contiene UUID en el path
        if (isProtectedPathWithoutUuid(pathInfo, method)) {
            logger.info("Protected path without UUID accessed: {}", pathInfo);
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el UUID del path
        String[] pathParts = pathInfo.split("/");
        String pathUuid = extractUuidFromPath(pathParts);

        if (pathUuid == null) {
            logger.warn("UUID not found in path: {}", pathInfo);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Extraer el UUID del token
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            logger.warn("No authentication found in SecurityContext");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Jwt jwt = (Jwt) authentication.getCredentials();
        String tokenUuid = jwt.getClaimAsString("sub");

        logger.info("Path UUID: {}", pathUuid);
        logger.info("Token UUID: {}", tokenUuid);

        if (tokenUuid.equals(pathUuid)) {
            filterChain.doFilter(request, response);
        } else {
            logger.warn("UUIDs do not match: Path UUID = {}, Token UUID = {}", pathUuid, tokenUuid);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean isPublicPath(String path, String method) {
        for (SecurityConstraint constraint : securityConstraintsProperties.getConstraints()) {
            if (constraint.getAuthRoles().contains("permitAll")) {
                for (SecurityCollection collection : constraint.getSecurityCollections()) {
                    if (collection.getMethods().contains(method) && matchesPattern(collection.getPatterns(), path)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isProtectedPathWithoutUuid(String path, String method) {
        for (SecurityConstraint constraint : securityConstraintsProperties.getConstraints()) {
            if (constraint.getAuthRoles().contains("authenticated")) {
                for (SecurityCollection collection : constraint.getSecurityCollections()) {
                    if (collection.getMethods().contains(method) && matchesPattern(collection.getPatterns(), path)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean matchesPattern(List<String> patterns, String path) {
        return patterns.stream().anyMatch(pattern -> path.matches(pattern.replace("*", ".*")));
    }

    private String extractUuidFromPath(String[] pathParts) {
        for (int i = 3; i <= 5; i++) {
            if (pathParts.length > i && pathParts[i].matches("^[0-9a-fA-F-]{36}$")) {
                return pathParts[i];
            }
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String pathInfo = request.getRequestURI();
        return !pathInfo.matches("^/api/v1/.*");
    }
}
