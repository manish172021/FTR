package com.ftr.WorkitemService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private JwtDecoder jwtDecoder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(userAuthoritiesMapper());
        return http.build();
    }

    private JwtAuthenticationConverter userAuthoritiesMapper() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            List<String> scopes = jwt.getClaimAsStringList("scp");
            List<String> groups = jwt.getClaimAsStringList("groups");
            if (scopes != null) {
                authorities.addAll(scopes.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
            }

            if (groups != null) {
                authorities.addAll(groups.stream()
                        .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                        .collect(Collectors.toList()));
            }
            return authorities;
        });
        return converter;
    }
}
