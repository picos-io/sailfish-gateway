package io.picos.sailfish.gateway.impl.cache.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.picos.sailfish.gateway.cache.CacheEvictService;
import io.picos.sailfish.gateway.message.TokenRevoked;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author dz
 */
public class TokenRevokedMessageListener implements MessageListener {

    private static final Log logger = LogFactory.getLog(TokenRevokedMessageListener.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CacheEvictService cacheEvictService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            TokenRevoked tokenRevoked = objectMapper.readValue(message.getBody(), TokenRevoked.class);
            if (tokenRevoked == null) {
                return;
            }
            final String token = tokenRevoked.getToken();
            logger.debug(String.format("Receiving revoked-token '%s'", token));
            cacheEvictService.cleanTokenData(token);
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
