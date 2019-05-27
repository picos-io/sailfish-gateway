package io.picos.sailfish.gateway.auth;

import io.picos.sailfish.gateway.model.Application;

import java.util.List;

public interface ApplicationService {

    /**
     *
     * @param applicationCode
     * @return
     */
    Application findByCode(String applicationCode);

    List<String> listApplications();

}
