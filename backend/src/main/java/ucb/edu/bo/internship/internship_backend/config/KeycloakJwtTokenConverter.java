package ucb.edu.bo.internship.internship_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    private final TokenConverterProperties properties;

    public KeycloakJwtTokenConverter(JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter, TokenConverterProperties properties) {
        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
        this.properties = properties;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt){
        Collection<GrantedAuthority> authorities = Stream.concat(
            jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
            extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    private String getPrincipalClaimName(Jwt jwt){
        return properties.getPrincipalAttribute()
            .map(jwt::getClaimAsString)
            .orElse(jwt.getClaimAsString(JwtClaimNames.SUB));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt){
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return Collections.emptySet();
        }
        Map<String, Object> resourceRoles = (Map<String, Object>) resourceAccess.get(properties.getResourceId());
        if (resourceRoles == null){
            resourceRoles = (Map<String, Object>) resourceAccess.get("internship-cliente");
            if (resourceRoles == null){
                resourceRoles = (Map<String, Object>) resourceAccess.get("internship_cliente");
            }
            if (resourceRoles == null){
                return Stream.of(new SimpleGrantedAuthority("ROLE_NOT_FOUND")).collect(Collectors.toSet());
            }
        }
        Collection<String> roles = (Collection<String>) resourceRoles.get("roles");
        if (roles == null) {
            return Collections.emptySet();
        }
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
    }

}
