package io.picos.sailfish.oauth2.test.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestCryptPassword {

    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(cryptPasswordEncoder.encode("gateway-secret"));
        Assert.assertTrue(cryptPasswordEncoder.matches("gateway-secret",
                                                       "$2a$10$9ev6ucvQ9dUkTuz9eQyJCuI9CzdMg1LpUGB4xQhW9WcrhhifM2bHa"));
    }
}
