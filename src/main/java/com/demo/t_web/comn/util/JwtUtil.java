package com.demo.t_web.comn.util;

import com.demo.t_web.comn.enums.JwtEnum;
import com.demo.t_web.comn.exception.JwtValidateException;
import com.demo.t_web.comn.model.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * com.demo.t_web.comn.util.JwtUtil
 *   - JwtUtil.java
 * </pre>
 *
 * @author : tarr4h
 * @className : JwtUtil
 * @description :
 * @date : 12/13/24
 */
@Component
@Slf4j
@AllArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public Cookie getJwtCookie() {
        return Utilities.getCookie("jwt");
    }

    public boolean checkTokenExist(){
        return getJwtCookie() != null;
    }

    public String getUserIdFromToken() {
        String userId = null;

        // use Cookie
        Cookie cookie = getJwtCookie();
        if(cookie != null){
            try {
                userId = extractUsername(cookie.getValue());
            } catch (IllegalArgumentException e) {
                throw new JwtValidateException(JwtEnum.NOT_SUITABLE_TOKEN.getValue(), e);
            } catch (ExpiredJwtException e) {
                throw new JwtValidateException(JwtEnum.JWT_TOKEN_EXPIRED.getValue(), e);
            } catch (Exception e){
                throw new JwtValidateException(JwtEnum.JWT_EXCEPTION.getValue(), e);
            }
        } else {
            throw new JwtValidateException(JwtEnum.JWT_TOKEN_NOT_FOUND.getValue());
        }

//        header 통해 token 전송 시
//        final String requestHeader = request.getHeader(JwtEnum.HEADER_STRING.getValue());
//        if(requestHeader != null && requestHeader.startsWith(JwtEnum.HEADER_PRE.getValue())){
//            jwtToken = requestHeader.substring(7);
//
//            try {
//                userId = jwtUtil.extractUsername(jwtToken);
//            } catch (IllegalArgumentException e) {
//                throw new JwtValidateException(JwtEnum.NOT_SUITABLE_TOKEN.getValue(), e);
//            } catch (ExpiredJwtException e) {
//                throw new JwtValidateException(JwtEnum.JWT_TOKEN_EXPIRED.getValue(), e);
//            } catch (Exception e){
//                log.error(JwtEnum.JWT_EXCEPTION.getValue(), e);
//            }
//        } else {
//            throw new JwtValidateException(JwtEnum.JWT_TOKEN_NOT_FOUND.getValue());
//        }

        return userId;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
       final Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSignKey().getBytes(StandardCharsets.UTF_8)).build()
                .parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(){
        return isTokenExpired(getJwtCookie().getValue());
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetail){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetail.getUsername());
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetail){
        return createToken(claims, userDetail.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30))) //30분
//                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 10))) //10초
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSignKey().getBytes(StandardCharsets.UTF_8)))
                .compact();

    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @SuppressWarnings("unchecked")
    private Authentication getUserFromToken(String token) {
        Claims claims = extractAllClaims(token);

        String username = extractUsername(token);
        List<String> roles = claims.get("roles", List.class);

        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
//                .map(v -> new SimpleGrantedAuthority(v))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);

    }
}
