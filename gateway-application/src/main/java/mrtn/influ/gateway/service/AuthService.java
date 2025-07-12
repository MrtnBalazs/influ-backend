package mrtn.influ.gateway.service;

import mrtn.influ.gateway.error.handling.GatewayAuthenticationException;
import mrtn.influ.gateway.error.handling.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    public static final String AUTH_TOKEN_HEADER = "X-Auth-Token";

    @Value("${auth.service.verify.endpoint}")
    private String tokenVerifyEndpointUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String validateToken(String token) {
        ResponseEntity<String> response = callAuthService(token);
        LOGGER.debug("Token validation response HTTP status code: {}, userId: {}, token: {}", response.getStatusCode(), response.getBody(), token);
        if(!response.getStatusCode().is2xxSuccessful() || Objects.isNull(response.getBody()))
            throw new TechnicalException("Problems with token validation! token: %s AuthService response code: %s".formatted(token, response.getStatusCode()));
        return response.getBody();
    }

    // TODO call with swagger generated client
    private ResponseEntity<String> callAuthService(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTH_TOKEN_HEADER, token);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(tokenVerifyEndpointUrl, HttpMethod.GET, entity, String.class);
        } catch (Exception exception) {
            LOGGER.error("Error happened during AuthService call");
            throw new TechnicalException(exception);
        }
    }
}
