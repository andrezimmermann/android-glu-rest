package com.github.andrezimmermann.gluappsample.client.list;

import com.github.andrezimmermann.gluappsample.client.mvp.AbstractSimplePresenter;
import com.github.andrezimmermann.gluappsample.client.mvp.event.ClickHandler;
import com.github.andrezimmermann.gluappsample.client.service.AsyncTaskResult;
import com.github.andrezimmermann.gluappsample.client.service.ServiceCallback;
import com.github.andrezimmermann.gluappsample.client.service.ServiceExecutor;
import com.github.andrezimmermann.gluappsample.server.api.GluApi;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;

import java.util.List;

/**
 * TODO: add javadoc
 */
public class ListPresenter extends AbstractSimplePresenter<ListView> {


    private final ServiceExecutor service;

    public ListPresenter(ListView view, GluApi service) {
        super(view);
        this.service = new ServiceExecutor(service);
    }

    @Override
    protected void onCreate(final ListView view) {
        view.getQueryButtonClick().addClickHandler(

                new ClickHandler() {
                    @Override
                    public void onClick() {
                        String text = view.getQueryInputText();


                        if (text == null || text.trim().isEmpty()) {
                            view.setQueryBlankError();
                        } else {
                            queryRouteIdByName(text, view);
                        }

                    }


                }

        );
    }

    private void queryRouteIdByName(String text, final ListView view) {
        ServiceCallback<List<BusLine>> callback = new ServiceCallback<List<BusLine>>(view) {

            @Override
            public void onResult(AsyncTaskResult<List<BusLine>> result) {

                if (result.getResult() != null) {
                    view.showResult(result.getResult());
                } else {
                    view.setServiceError();
                }

            }
        };

        service.getRouteIdByName(callback, text);
    }

    @Override
    protected void onResume(ListView view) {
        //NOOP
    }

    @Override
    protected void onPause(ListView view) {
        //NOOP
    }


}
