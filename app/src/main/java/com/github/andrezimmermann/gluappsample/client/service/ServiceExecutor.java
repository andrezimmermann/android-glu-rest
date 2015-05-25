package com.github.andrezimmermann.gluappsample.client.service;

import android.os.AsyncTask;

import com.github.andrezimmermann.gluappsample.server.api.GluApi;
import com.github.andrezimmermann.gluappsample.shared.data.BusDeparture;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;
import com.github.andrezimmermann.gluappsample.shared.data.BusStop;

import java.util.List;

/**
 * TODO: add javadoc
 */
public class ServiceExecutor {

    private GluApi gluApi;

    public ServiceExecutor(GluApi gluApi) {
        this.gluApi = gluApi;
    }

    public void getRouteDetails(final ServiceCallback<BusDetail> serviceCallback, final int routeId) {
        AsyncTask<Integer, Void, AsyncTaskResult<BusDetail>> asyncTask = new AsyncTask<Integer, Void, AsyncTaskResult<BusDetail>>() {

            @Override
            protected void onPostExecute(AsyncTaskResult<BusDetail> result) {
                serviceCallback.getProgressIndicator().stopProgress();
                serviceCallback.onResult(result);
            }

            @Override
            protected void onPreExecute() {
                serviceCallback.getProgressIndicator().startProgress();
            }

            @Override
            protected AsyncTaskResult<BusDetail> doInBackground(Integer... params) {
                try {
                    BusDetail busDetail = new BusDetail();

                    List<BusDeparture> departures = gluApi.getDeparturesByRouteId(routeId);
                    List<BusStop> stops = gluApi.getStopsByRouteId(routeId);

                    busDetail.setStops(stops);
                    busDetail.setDepartures(departures);

                    return new AsyncTaskResult<BusDetail>(busDetail);
                } catch (Exception e) {
                    return new AsyncTaskResult<BusDetail>(e);
                }
            }
        };

        asyncTask.execute(routeId);



    }

    public void getRouteIdByName(final ServiceCallback<List<BusLine>> serviceCallback, final String routeName) {

        AsyncTask<String, Void, AsyncTaskResult<List<BusLine>>> asyncTask = new AsyncTask<String, Void, AsyncTaskResult<List<BusLine>>>() {

            @Override
            protected void onPostExecute(AsyncTaskResult<List<BusLine>> result) {
                serviceCallback.getProgressIndicator().stopProgress();
                serviceCallback.onResult(result);
            }

            @Override
            protected void onPreExecute() {
                serviceCallback.getProgressIndicator().startProgress();
            }

            @Override
            protected AsyncTaskResult<List<BusLine>> doInBackground(String... params) {
                try {
                    List<BusLine> busLines = gluApi.getRouteIdByName(routeName);
                    return new AsyncTaskResult<List<BusLine>>(busLines);
                } catch (Exception e) {
                    return new AsyncTaskResult<List<BusLine>>(e);
                }
            }
        };

        asyncTask.execute(routeName);


    }

}
