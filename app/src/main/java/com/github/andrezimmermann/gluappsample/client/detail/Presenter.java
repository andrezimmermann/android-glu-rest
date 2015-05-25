package com.github.andrezimmermann.gluappsample.client.detail;

import com.github.andrezimmermann.gluappsample.client.mvp.AbstractSimplePresenter;
import com.github.andrezimmermann.gluappsample.client.service.AsyncTaskResult;
import com.github.andrezimmermann.gluappsample.client.service.BusDetail;
import com.github.andrezimmermann.gluappsample.client.service.ServiceCallback;
import com.github.andrezimmermann.gluappsample.client.service.ServiceExecutor;
import com.github.andrezimmermann.gluappsample.server.api.GluApi;

/**
 * TODO: add javadoc
 */
public class Presenter extends AbstractSimplePresenter<RouteDetailView> implements OnDetailHandler {

    private final ServiceExecutor service;

    public Presenter(RouteDetailView view, GluApi instance) {
        super(view);


        service = new ServiceExecutor(instance);
    }

    @Override
    protected void onCreate(RouteDetailView view) {
        //NOOP
    }



    @Override
    public void onDetailRequest(int id, String description) {

        final RouteDetailView view = getView();

        view.setHeader(description);

        ServiceCallback<BusDetail> callback = new ServiceCallback<BusDetail>(view) {
            @Override
            public void onResult(AsyncTaskResult<BusDetail> result) {
                if (result.getError() != null) {
                    view.showServiceError();
                } else {
                    view.showDetails(result.getResult());
                }
            }
        };

        service.getRouteDetails(callback, id);


    }
}
