package ucb.edu.bo.internship.internship_backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ucb.edu.bo.internship.internship_backend.bl.UsuariosBL;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfiguration {
    private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;

    private final Logger logger = LoggerFactory.getLogger(GlobalSecurityConfiguration.class);

    private final SecurityConstraintsProperties securityConstraintsProperties;
    private final UsuariosBL usuariosBL;
    private final UuidPathSecurityFilter uuidPathSecurityFilter;
    private final RosterSecurityFilter rosterSecurityFilter;

    @Value("${FRONTEND_URL}")
    private String clientUrl;
    public GlobalSecurityConfiguration(TokenConverterProperties properties, SecurityConstraintsProperties securityConstraintsProperties, UsuariosBL usuariosBL, UuidPathSecurityFilter uuidPathSecurityFilter, RosterSecurityFilter rosterSecurityFilter) {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
                = new JwtGrantedAuthoritiesConverter();
        this.keycloakJwtTokenConverter
                = new KeycloakJwtTokenConverter(
                jwtGrantedAuthoritiesConverter,
                properties);
        this.securityConstraintsProperties = securityConstraintsProperties;
        this.usuariosBL = usuariosBL;
        this.uuidPathSecurityFilter = uuidPathSecurityFilter;
        this.rosterSecurityFilter = rosterSecurityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        List<SecurityConstraint> securityConstraints = securityConstraintsProperties.getConstraints();

        if (securityConstraints == null){
            logger.info("No security constraints found");
            return http.authorizeHttpRequests( (authorizeHttpRequests) -> {
                authorizeHttpRequests.anyRequest().authenticated();
            }).oauth2ResourceServer( (oauth2) -> {
                oauth2.jwt( (jwt) -> jwt.jwtAuthenticationConverter(keycloakJwtTokenConverter));
            }).sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
        }

        return http.authorizeHttpRequests( (authorizeHttpRequests) -> {
                    securityConstraints.forEach( (constraint) -> {
                        try {
                            List<String> authRoles = constraint.getAuthRoles();
                            List<SecurityCollection> securityCollections = constraint.getSecurityCollections();

                            securityCollections.forEach(collection -> {
                                String name = collection.getName();
                                List<String> patterns = collection.getPatterns();
                                List<String> methods = collection.getMethods();

                                List<HttpMethod> httpMethods = new ArrayList<>();

                                for (String method: methods
                                ) {
                                    switch (method) {
                                        case "GET":
                                            httpMethods.add(HttpMethod.GET);
                                            break;
                                        case "POST":
                                            httpMethods.add(HttpMethod.POST);
                                            break;
                                        case "PUT":
                                            httpMethods.add(HttpMethod.PUT);
                                            break;
                                        case "DELETE":
                                            httpMethods.add(HttpMethod.DELETE);
                                            break;
                                        case "PATCH":
                                            httpMethods.add(HttpMethod.PATCH);
                                            break;
                                        default:
                                            break;
                                    }
                                }

                                if (httpMethods.isEmpty()) {
                                    if(authRoles.size() == 1){
                                        String role = authRoles.get(0);
                                        if(role.equals("permitAll")){
                                            logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration: permitAll with no methods");
                                            authorizeHttpRequests
                                                    .requestMatchers(patterns.toArray(new String[0]))
                                                    .permitAll();
                                        }else{
                                            if(role.equals("authenticated")){
                                                logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration: authenticated with no methods");
                                                authorizeHttpRequests
                                                        .requestMatchers(patterns.toArray(new String[0]))
                                                        .authenticated();
                                            }else{
                                                logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration: " + authRoles + " with no methods");
                                                authorizeHttpRequests
                                                        .requestMatchers(patterns.toArray(new String[0]))
                                                        .hasAnyRole(authRoles.toArray(new String[0]));
                                            }
                                        }
                                    }else{
                                        logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration:" + authRoles + " with no methods");
                                        authorizeHttpRequests
                                                .requestMatchers(patterns.toArray(new String[0]))
                                                .hasAnyRole(authRoles.toArray(new String[0]));
                                    }
                                }else{
                                    if (authRoles.size() == 1) {
                                        String role = authRoles.get(0);
                                        if (role.equals("permitAll")) {
                                            logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration: permitAll with methods: " + httpMethods);
                                            for (HttpMethod httpMethod: httpMethods
                                            ) {
                                                authorizeHttpRequests
                                                        .requestMatchers(httpMethod, patterns.toArray(new String[0]))
                                                        .permitAll();
                                            }
                                        }else{
                                            if(role.equals("authenticated")) {
                                                logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration: authenticated with methods: " + httpMethods);
                                                for (HttpMethod httpMethod : httpMethods
                                                ) {
                                                    authorizeHttpRequests
                                                            .requestMatchers(httpMethod, patterns.toArray(new String[0]))
                                                            .authenticated();
                                                }
                                            }else{
                                                logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration: " + authRoles + " with methods: " + httpMethods);
                                                for (HttpMethod httpMethod: httpMethods
                                                ) {
                                                    authorizeHttpRequests
                                                            .requestMatchers(httpMethod, patterns.toArray(new String[0]))
                                                            .hasAnyRole(authRoles.toArray(new String[0]));
                                                }
                                            }
                                        }
                                    }else{
                                        logger.info("CONFIGURATION name: " + name + " patterns: " + patterns + " configuration:" + authRoles + "with methods: " + httpMethods);
                                        for (HttpMethod httpMethod: httpMethods
                                        ) {
                                            authorizeHttpRequests
                                                    .requestMatchers(httpMethod, patterns.toArray(new String[0]))
                                                    .hasAnyRole(authRoles.toArray(new String[0]));
                                        }
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .oauth2ResourceServer( (oauth2) -> {oauth2.jwt( (jwt) -> jwt.jwtAuthenticationConverter(keycloakJwtTokenConverter));})
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(uuidPathSecurityFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(rosterSecurityFilter, BasicAuthenticationFilter.class)
//                .addFilterBefore(new RegistrationCompletionFilter(keycloakJwtTokenConverter, usuariosBL, clientUrl), BasicAuthenticationFilter.class)
                .build();
    }
}
