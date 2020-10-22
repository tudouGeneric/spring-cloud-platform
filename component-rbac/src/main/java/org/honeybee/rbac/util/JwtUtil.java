package org.honeybee.rbac.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.rbac.pojo.JwtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class JwtUtil {

    private static final String CLAIM_KEY_ACCOUNT = "account";
    private static final String CLAIM_KEY_USERNAME = "name";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USER_ID = "id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";

    private Map<String, String> tokenMap = new ConcurrentHashMap<>(32);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long access_token_expiration;

    @Value("${jwt.expiration}")
    private Long refresh_token_expiration;

    //jwt签名算法
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    /**
     * 获取当前用户
     * @return
     */
    public static JwtUser getUserInfo() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public JwtUser getUserFromToken(String token) {
        JwtUser userDetail;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            Long userId = Long.parseLong(String.valueOf(claims.get(CLAIM_KEY_USER_ID)));
            String userName = String.valueOf(claims.get(CLAIM_KEY_USERNAME));
            String account = claims.getSubject();
            List<String> authorities = (List<String>) claims.get(CLAIM_KEY_AUTHORITIES);
            userDetail = new JwtUser(userId, userName, account, authorities);
        } catch (Exception e) {
            userDetail = null;
        }
        return userDetail;
    }

    /**
     * 根据token获取用户Id
     * @param token
     * @return
     */
    public long getUserIdFromToken(String token) {
        long userId = 0;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            userId = Long.parseLong(String.valueOf(claims.get(CLAIM_KEY_USER_ID)));
        } catch (Exception e) {
            throw new RuntimeException("获取用户id异常: {}", e);
        }
        return userId;
    }

    /**
     * 根据token获取用户账号
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String account = null;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            account = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("获取用户账号异常: {}", e);
        }
        return account;
    }

    /**
     * 获取token创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 根据jwtUser生成token
     * @param userDetail
     * @return
     */
    public String generateAccessToken(JwtUser userDetail) {
        Map<String, Object> claims = this.generateClaims(userDetail);
        claims.put(CLAIM_KEY_AUTHORITIES, this.authoritiesToArray(userDetail.getAuthorities()));
        String accessToken = this.generateAccessToken(userDetail.getUsername(), claims);
        //存储Token
        putToken(userDetail.getUsername(), accessToken);
        return accessToken;
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            throw new RuntimeException("获取token过期时间异常: {}", e);
        }
        return expiration;
    }

    /**
     * token是否可以刷新
     * @param token
     * @param lastPasswordReset
     * @return
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return (!isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token));
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(), claims);
            //存储token
            putToken(claims.getSubject(), refreshedToken);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 校验token是否失效
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public void putToken(String userName, String token) {
        tokenMap.put(userName, token);
    }

    public void deleteToken(String userName) {
        tokenMap.remove(userName);
    }

    public boolean containToken(String userName, String token) {
        if(userName != null && tokenMap.containsKey(userName) && tokenMap.get(userName).equals(token)) {
            return true;
        }
        return false;
    }


    /**
     * 获取Claims
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * token是否过期
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 判断时间是否在密码最后一次更新时间之前
     * @param created
     * @param lastPasswordReset
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Map<String, Object> generateClaims(JwtUser userDetail) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_ACCOUNT, userDetail.getUsername());
        claims.put(CLAIM_KEY_USERNAME, userDetail.getName());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USER_ID, userDetail.getId());
        return claims;
    }

    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return this.generateToken(subject, claims, access_token_expiration);
    }

    /**
     * 生成token
     * @param subject
     * @param claims
     * @param expiration
     * @return
     */
    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(this.generateExpirationDate(expiration))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    private Set authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        Set<String> list = new HashSet<>();
        for(GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }

}
