package com.example.kakao.core.utils;

import org.junit.jupiter.api.Test;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kakao._core.utils.JwtTokenUtils;
import com.example.kakao.user.User;

public class JwtTokenUtilsTest {

    @Test
    public void jwt_create_and_verify() {
        User user = User.builder().id(1).email("ssar@nate.com").build();
        String jwt = JwtTokenUtils.create(user);
        System.out.println(jwt);

        DecodedJWT decodedJWT = JwtTokenUtils.verify(jwt);

        int id = decodedJWT.getClaim("id").asInt();
        String email = decodedJWT.getClaim("email").asString();
        System.out.println("id = " + id);
        System.out.println("email = " + email);
    }

}
