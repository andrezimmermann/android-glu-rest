package com.github.andrezimmermann.gluappsample.client.service;

/**
 * TODO: add javadoc
 */
public abstract class ServiceCallback<T> {

    private HasProgressIndicator progressIndicator;

    public ServiceCallback(HasProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    public abstract void onResult(AsyncTaskResult<T> result);

    public HasProgressIndicator getProgressIndicator() {

        return progressIndicator;
    }
}
