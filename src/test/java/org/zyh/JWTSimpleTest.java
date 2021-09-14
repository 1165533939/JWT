package org.zyh;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTSimpleTest {

    private static String jwtString = "";

    @Test
    public void testGetAndVerifyJWT() {
        Map<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 10);

        String token = JWT.create()
                .withHeader(map) // header
                .withClaim("userId", 123456789) // payload
                .withClaim("username", "Edge Man") // payload
                .withExpiresAt(instance.getTime()) // 设置令牌过期时间
                .sign(Algorithm.HMAC256("$^%SAI*JA")); // 签名

        Assert.assertNotNull(token);
        jwtString = token;
        System.out.println(token);
        testVerifyJWT();
    }

    public void testVerifyJWT() {
        // 创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("$^%SAI*JA")).build();

        DecodedJWT verify = jwtVerifier.verify(jwtString);

        Assert.assertEquals(123456789, verify.getClaim("userId").asInt().intValue());
        Assert.assertEquals("Edge Man", verify.getClaim("username").asString());

        System.out.println(verify.getClaim("userId"));
        System.out.println(verify.getClaim("username"));
        System.out.println("过期时间：" + verify.getExpiresAt());
    }
}

