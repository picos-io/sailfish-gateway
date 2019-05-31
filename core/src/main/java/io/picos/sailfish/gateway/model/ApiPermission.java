package io.picos.sailfish.gateway.model;

public interface ApiPermission {

    /**
     *
     * @return the code of the application
     */
    String getApplication();

    String getMethod();

    String getApiUri();

}
