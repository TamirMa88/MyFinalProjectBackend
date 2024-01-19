package tamir.ma.tamir.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@PropertySource("classpath:application.properties")
public class JwtUtilities {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expires}")
    private long expires;



    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }



    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isValidToken(String token) {
        Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return true;
    }

    public String generateToken(String email, List<String> roles) {
        return Jwts.builder()
                .setSubject(email).claim("role", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(expires, ChronoUnit.MILLIS)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }



    public String getToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization"); // Bearer askrn1ok3nrkdnsfgjk
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
