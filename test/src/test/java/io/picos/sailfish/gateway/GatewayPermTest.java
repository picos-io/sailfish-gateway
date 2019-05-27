package io.picos.sailfish.gateway;

import io.picos.sailfish.gateway.auth.AuthzService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayTestApplication.class)
public class GatewayPermTest {

    @Autowired
    private AuthzService authzService;

    @Test
    public void testAdminPerm() {
        Assert.assertNotNull(authzService.authorize("admin", "iamsvc", "GET", "/mytestapp/hello-world"));
    }

}
