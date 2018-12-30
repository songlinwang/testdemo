package example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author wsl
 * @date 2018/12/28
 */
public class JwtUtil {

    /**
     * 签发jwt令牌
     *
     * @param uid
     * @param userName
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public static String createJwt(String uid, String userName, long ttlMillis) throws Exception {
        // 指定签名时候的签名算法 也就是header的部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);
        // 创建payload的私有声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", uid);
        claims.put("user_name", userName);
        String id = UUID.randomUUID().toString(); // jwt的id
        SecretKey secretKey = genSecretKey();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(nowDate) // 签发时间
                .setSubject(uid)//代表jwt主题 这个是一个json串 可以放一些userid 作为用户的唯一标识
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expirMillis = now + ttlMillis;
            Date expire = new Date(expirMillis);
            builder.setExpiration(expire);
        }
        return builder.compact();

    }

    /**
     * 字符串生成加密的key
     *
     * @return
     */
    public static SecretKey genSecretKey() {
        String secret = new String("123456789");// 可以从配置文件中读取
        byte[] encodeKey = Base64Utils.decodeFromString(secret);
        System.out.println(encodeKey);
        System.out.println(Base64Utils.decodeFromUrlSafeString(secret));
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    /**
     * 解析jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = genSecretKey();// 签名秘钥 和生成签名的秘钥一模一样
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        return claims;
    }
}
