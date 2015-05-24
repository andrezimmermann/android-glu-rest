package com.github.andrezimmermann.gluappsample.server;

/**
 * Error on the Service
 */
public class ServiceUnavaiableException extends Exception {

    public ServiceUnavaiableException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServiceUnavaiableException(String detailMessage) {
        super(detailMessage);
    }

    public ServiceUnavaiableException(Throwable throwable) {
        super(throwable);
    }
}
