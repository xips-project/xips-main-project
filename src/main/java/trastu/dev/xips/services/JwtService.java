package trastu.dev.xips.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trastu.dev.xips.entities.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {

    private static final String SECRET_KEY="Wdp5hlIb5BJL8oMgs+UnJDXh50qEFp8Wq+MqLJL0e8Y=";


    public String generateToken(User user, Map<String, Object> extraClaims) {

        Date issuedAtDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(issuedAtDate.getTime() + (1800000));

        return  Jwts.builder()
                    .claims(extraClaims)
                    .subject(user.getUsername())
                    .issuedAt(issuedAtDate)
                    .expiration(expirationDate)
                    .signWith(generateKey())
                    .compact();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();

    }
}
