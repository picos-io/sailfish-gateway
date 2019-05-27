package io.picos.sailfish.gateway.cache;

public interface CacheEvictService {

    void cleanAllTokenData();

    void cleanAllUserData();

    void cleanUserData(String username, String application);

    void cleanTokenData(String token);
}
