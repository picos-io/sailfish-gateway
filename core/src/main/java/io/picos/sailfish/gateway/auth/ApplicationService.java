package io.picos.sailfish.gateway.auth;

import io.picos.sailfish.gateway.model.Application;

import java.util.List;

public interface ApplicationService {

    /**
     * Find the application by code
     *
     * @param applicationCode
     * @return
     */
    Application findByCode(String applicationCode);

    /**
     * List all the applications
     *
     * @return
     */
    List<String> listApplications();

}
