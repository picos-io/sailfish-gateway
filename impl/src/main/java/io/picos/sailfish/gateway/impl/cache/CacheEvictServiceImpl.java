package io.picos.sailfish.gateway.impl.cache;

import io.picos.sailfish.gateway.cache.CacheEvictService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * @author dz
 */
public class CacheEvictServiceImpl implements CacheEvictService, InitializingBean {

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Override
    public void cleanUserData(String username, String application) {
        if (cacheManager == null) {
            return;
        }
        Cache cache = cacheManager.getCache(CacheConstants.USER_CACHE);
        if (cache != null) {
            cache.evict(username + "_" + application);
        }
    }

    @Override
    public void cleanTokenData(String token) {
        if (cacheManager == null) {
            return;
        }
        Cache cache = cacheManager.getCache(CacheConstants.TOKEN_CACHE);
        if (cache != null) {
            cache.evict(token);
        }
    }

    @Override
    public void cleanAllTokenData() {
        if (cacheManager == null) {
            return;
        }
        Cache cache = cacheManager.getCache(CacheConstants.TOKEN_CACHE);
        if (cache != null) {
            cache.clear();
        }
    }

    @Override
    public void cleanAllUserData() {
        if (cacheManager == null) {
            return;
        }
        Cache cache = cacheManager.getCache(CacheConstants.USER_CACHE);
        if (cache != null) {
            cache.clear();
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (cacheManager == null) {
            return;
        }
        //always clear the cache after restart
        Cache cache = cacheManager.getCache(CacheConstants.USER_CACHE);
        if (cache != null) {
            cache.clear();
        }
        cache = cacheManager.getCache(CacheConstants.TOKEN_CACHE);
        if (cache != null) {
            cache.clear();
        }
    }

}
