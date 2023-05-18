package ali.project.railway_project.Security;

import ali.project.railway_project.Service.AuthService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    AuthService authService;
    @Value("${app.jwtSecretKey}")
    private String kalitUzingizBratan;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String tokenxon = request.getHeader("Authorization");
            if (tokenxon != null) {
                if (tokenxon.substring(0, 6).equals("Bearer")) {
                    tokenxon = tokenxon.substring(7);
                    if (tokenValidmi(tokenxon)) {
                        String userId = getUserIdTokenningIchidan(tokenxon);
                        UserDetails userDetails = authService.getUserById(UUID.fromString(userId));
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        (null),
                                        userDetails.getAuthorities()
                                );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("could not set user authintication in security context", e);
        }
        filterChain.doFilter(request, response);
    }

    public boolean tokenValidmi(String tokenxon) {
        try {
            Jwts.parser()
                    .setSigningKey(kalitUzingizBratan)
                    .parseClaimsJws(tokenxon);
            return true;
        } catch (SignatureException ex) {
            logger.error("invalid jwt signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid jwt token");
        } catch (ExpiredJwtException ex) {
            logger.error("expire jwt token");
        } catch (UnsupportedJwtException ex) {
            logger.error("unsupported jwt token");
        } catch (IllegalArgumentException ex) {
            logger.error("jwt claims string is empty");
        }
        return false;
    }

    public String getUserIdTokenningIchidan(String token) {
        return Jwts.parser()
                .setSigningKey(kalitUzingizBratan)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
