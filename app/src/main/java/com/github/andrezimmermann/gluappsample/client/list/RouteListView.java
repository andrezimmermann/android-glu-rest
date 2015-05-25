package com.github.andrezimmermann.gluappsample.client.list;

import com.github.andrezimmermann.gluappsample.client.mvp.View;
import com.github.andrezimmermann.gluappsample.client.mvp.event.HasClickHandlers;
import com.github.andrezimmermann.gluappsample.client.mvp.event.HasListItemSelectionHandlers;
import com.github.andrezimmermann.gluappsample.client.service.HasProgressIndicator;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;

import java.util.List;

/**
 * TODO: add javadoc
 */
public interface RouteListView extends View, HasProgressIndicator {

    HasClickHandlers getQueryButtonClick();

    void setQueryBlankError();

    String getQueryInputText();

    void setServiceError();

    void showResult(List<BusLine> routes);

    HasListItemSelectionHandlers getBusListSelecion();

    void showDetails(BusLine data);
}
