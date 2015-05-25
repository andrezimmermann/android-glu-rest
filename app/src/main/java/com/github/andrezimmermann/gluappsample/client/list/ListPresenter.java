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
public class ListPresenter extends AbstractSimplePresenter<RouteListView> {


    private final ServiceExecutor service;

    public ListPresenter(RouteListView view, GluApi service) {
        super(view);
        this.service = new ServiceExecutor(service);
    }

    @Override
    protected void onCreate(final RouteListView view) {
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

        view.getBusListSelecion().addListItemSelecionHandler(new BusLineSelecionHandler() {

            @Override
            public void onListSelecion(BusLine data) {
                view.showDetails(data);
            }
        });
    }

    private void queryRouteIdByName(String text, final RouteListView view) {
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
    protected void onResume(RouteListView view) {
        //NOOP
    }

    @Override
    protected void onPause(RouteListView view) {
        //NOOP
    }


}
