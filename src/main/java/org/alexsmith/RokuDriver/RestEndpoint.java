package org.alexsmith.RokuDriver;

public enum RestEndpoint {
    APPS("apps"),
    CURRENT_APP("current_app"),
    INSTALL("install"),
    LAUNCH("launch"),
    KEYPRESS("press"),
    GET_ELEMENT("element"),
    ACTIVE_ELEMENT("element/active"),
    DEEPLINK_INPUT("input"),
    WEBDRIVER_STATUS("status"),
    SESSIONS("sessions"),
    START_SESSION("session"),
    PAGE_SOURCE("source"),
    SET_PRESS_DELAY("timeouts"),
    SET_SEQUENCE_PRESS_DELAY("timeouts/press_wait"),
    TIMEOUT("timeouts/implicit_wait");

    public final String endpoint;

    private RestEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
