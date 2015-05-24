package com.github.andrezimmermann.gluappsample.server.api.error;

/**
 * Error on the Service
 */
public class ServiceException extends Exception {

    public ServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ServiceException(String detailMessage) {
        super(detailMessage);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }
}
