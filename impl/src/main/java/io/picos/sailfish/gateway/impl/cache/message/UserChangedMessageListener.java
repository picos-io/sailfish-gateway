package io.picos.sailfish.gateway.impl.cache.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.picos.sailfish.gateway.auth.ApplicationService;
import io.picos.sailfish.gateway.cache.CacheEvictService;
import io.picos.sailfish.gateway.message.UserChanged;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class UserChangedMessageListener implements MessageListener {

    private static final Log logger = LogFactory.getLog(UserChangedMessageListener.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CacheEvictService cacheEvictService;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            UserChanged userChanged = objectMapper.readValue(message.getBody(), UserChanged.class);
            if (userChanged == null || userChanged.getUsername() == null) {
                return;
            }

            logger.debug(String.format("Receiving changed user: %s", userChanged));

            applicationService.listApplications()
                              .forEach(application -> cacheEvictService.cleanUserData(userChanged.getUsername(),
                                                                                      application));

        } catch (Exception e) {
            logger.error(e);
        }
    }

}
