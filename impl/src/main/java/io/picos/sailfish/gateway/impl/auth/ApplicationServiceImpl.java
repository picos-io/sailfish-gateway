package io.picos.sailfish.gateway.impl.auth;

import io.picos.sailfish.gateway.auth.ApplicationService;
import io.picos.sailfish.gateway.impl.mongodb.repository.ApplicationRepository;
import io.picos.sailfish.gateway.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dz
 */
@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<String> listApplications() {
        return (List) applicationRepository.findAll();
    }

    @Override
    public Application findByCode(String applicationCode) {
        return applicationRepository.findByCode(applicationCode);
    }

}
