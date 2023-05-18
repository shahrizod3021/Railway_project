package ali.project.railway_project.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecretKey}")
    private String kalitUzingizBratan;

    @Value("${app.jwtExpireInMilSec}")
    private Long expireTime;

    public String generateToken(UUID id) {
        Date yashashMuddati = new Date(new Date().getTime() + expireTime);
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(yashashMuddati)
                .signWith(SignatureAlgorithm.HS512, kalitUzingizBratan)
                .compact();
    }
}
