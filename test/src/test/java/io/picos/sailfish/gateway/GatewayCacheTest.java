package io.picos.sailfish.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.picos.sailfish.gateway.message.TokenRevoked;
import io.picos.sailfish.gateway.message.UserChanged;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayTestApplication.class)
public class GatewayCacheTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCacheChanged() throws Exception {
        StringRedisTemplate template = applicationContext.getBean(StringRedisTemplate.class);
        String message = objectMapper.writeValueAsString(new TokenRevoked("121423213"));
        template.convertAndSend("gateway_token_revoked", message);
        System.out.println(String.format("send to channel[%s] with message[%s]",
                                         "gateway_token_revoked",
                                         message));
    }

    @Test
    public void testCacheUserApplication() throws Exception {
        StringRedisTemplate template = applicationContext.getBean(StringRedisTemplate.class);
        String message = objectMapper.writeValueAsString(new UserChanged("id", "admin"));
        template.convertAndSend("gateway_user_changed", message);
        System.out.println(String.format("send to channel[%s] with message[%s]",
                                         "gateway_user_changed",
                                         message));
    }

}
