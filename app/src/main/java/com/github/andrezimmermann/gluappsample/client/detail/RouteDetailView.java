package com.github.andrezimmermann.gluappsample.client.detail;

import com.github.andrezimmermann.gluappsample.client.mvp.View;
import com.github.andrezimmermann.gluappsample.client.service.BusDetail;
import com.github.andrezimmermann.gluappsample.client.service.HasProgressIndicator;

/**
 * TODO: add javadoc
 */
public interface RouteDetailView extends View, HasProgressIndicator {

    void setHeader(String description);

    void showServiceError();

    void showDetails(BusDetail result);
}
