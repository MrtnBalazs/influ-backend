package mrtn.influ.gateway.service;

import mrtn.influ.gateway.error.handling.GatewayBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    public static final String TOKEN_HEADER = "AuthToken";

    @Value("${auth.service.verify.endpoint}")
    private String tokenVerifyEndpointUrl;

    public void validateToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(TOKEN_HEADER, token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(tokenVerifyEndpointUrl, HttpMethod.GET, entity, Void.class);
        LOGGER.debug("Token validation response HTTP status code: {}", response.getStatusCode());
        if(!response.getStatusCode().is2xxSuccessful())
            throw new GatewayBusinessException("Token invalid!");
    }
}
