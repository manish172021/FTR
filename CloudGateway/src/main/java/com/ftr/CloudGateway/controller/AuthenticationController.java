package com.ftr.CloudGateway.controller;

import ch.qos.logback.core.model.Model;
import com.ftr.CloudGateway.model.AuthenticationResponse;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @AuthenticationPrincipal OidcUser oidcUser,
            Model model,
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client) throws ParseException {
        // BELOW CODE IS ONLY SETTING GROUP IN ROLE BUT IT IS NOT PROPPAGATING TO OTHER MS
        //String accessToken = client.getAccessToken().getTokenValue();
//        String[] tokenParts = accessToken.split("\\.");
//        String encodedPayload = tokenParts[1];
//        String decodedPayload = new String(Base64.getUrlDecoder().decode(encodedPayload), StandardCharsets.UTF_8);
//
//        JSONParser parser = new JSONParser();
//        JSONObject payloadJson = (JSONObject) parser.parse(decodedPayload);
//        JSONArray groupsJson = (JSONArray) payloadJson.get("groups");
//        List<String> groups = groupsJson.stream().map(Object::toString).collect(Collectors.toList());
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        groups.forEach(group -> authorities.add(new SimpleGrantedAuthority("ROLE_" + group))); // Add authorities for groups

//        AuthenticationResponse authenticationResponse
//                = AuthenticationResponse.builder()
//                .userId(oidcUser.getEmail())
//                .accessToken(client.getAccessToken().getTokenValue())
//                .refreshToken(client.getRefreshToken().getTokenValue())
//                .expiresAt(client.getAccessToken().getExpiresAt().getEpochSecond())
//                .authorityList(Stream.concat(
//                            authorities.stream().map(GrantedAuthority::getAuthority),
//                            oidcUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//                        ).collect(Collectors.toList()))
//                .build();
        AuthenticationResponse authenticationResponse
                = AuthenticationResponse.builder()
                .userId(oidcUser.getEmail())
                .accessToken(client.getAccessToken().getTokenValue())
                .refreshToken(client.getRefreshToken().getTokenValue())
                .expiresAt(client.getAccessToken().getExpiresAt().getEpochSecond())
                .authorityList(oidcUser.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
