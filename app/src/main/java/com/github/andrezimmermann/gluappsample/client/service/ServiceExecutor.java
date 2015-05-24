package com.github.andrezimmermann.gluappsample.client.service;

import android.os.AsyncTask;

import com.github.andrezimmermann.gluappsample.server.api.GluApi;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;

import java.util.List;

/**
 * TODO: add javadoc
 */
public class ServiceExecutor {

    private GluApi gluApi;

    public ServiceExecutor(GluApi gluApi) {
        this.gluApi = gluApi;
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
