package mrtn.influ.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    //@Value("${authToken.secret}")
    //private char[] secret;
    //private final SecretKey secret = Jwts.SIG.HS256.key().build();
    private final String ISSUER = "INFLU_AUTH_SERVICE";
    // TODO generate something
    private final SecretKey SECRET = Keys.hmacShaKeyFor("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SECRET).build().parseSignedClaims(token).getPayload();
    }

    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token) && validateIssuer(token);
        } catch (Exception e) {
            LOGGER.error("Token validation error: ", e);
            return false;
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Boolean validateIssuer(String token) {
        return extractIssuer(token).equals(ISSUER);
    }

    public String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuer(ISSUER)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 5000 * 60 * 60 * 10))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(SECRET)
                .compact();
    }
}
