package com.example.kakao._core.utils;

import java.time.Instant;

import org.springframework.core.env.Environment;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kakao.user.User;

public class JwtTokenUtils {

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject("metacoding-key")
                .withClaim("id", user.getId()) // 찾고자 하는 값
                .withClaim("email", user.getEmail())
                .withExpiresAt(Instant.now().plusMillis(1000))// 토큰의 만료시간 (1000=1초)
                .sign(Algorithm.HMAC512("meta"));
        // 토큰의 시간을 길게 잡으면 길게 잡는 만큼 인증 없이 사용할 수 있음
        // 클라이언트의 상태를 저장하지 않기 때문에 탈취당하면 방법이 없음
        return "Bearer " + jwt;
    }

    public static DecodedJWT verify(String jwt)
            throws SignatureVerificationException, TokenExpiredException {
        jwt = jwt.replace("Bearer ", ""); // 배리어 인증방식
        // JWT를 검증한 후, 검증이 완료되면, Header와 payload를 base64로 복호화함
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("meta"))
                .build().verify(jwt);
        // String이 아닌 Object로 받을 경우 getter로 호출해서 사용할 수 있음
        return decodedJWT;
        // 헤더와 페이로드가 디코딩 됨 (이건 누구나할 수 있음)
    }

}