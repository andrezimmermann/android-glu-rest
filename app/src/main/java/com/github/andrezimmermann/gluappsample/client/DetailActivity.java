package com.github.andrezimmermann.gluappsample.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.github.andrezimmermann.gluappsample.R;
import com.github.andrezimmermann.gluappsample.client.detail.Presenter;
import com.github.andrezimmermann.gluappsample.client.detail.RouteDetailView;
import com.github.andrezimmermann.gluappsample.client.service.BusDetail;
import com.github.andrezimmermann.gluappsample.client.service.GluApiSingleton;
import com.github.andrezimmermann.gluappsample.client.widgets.ListUtils;
import com.github.andrezimmermann.gluappsample.client.widgets.SimpleAdapter;
import com.github.andrezimmermann.gluappsample.shared.data.BusDeparture;
import com.github.andrezimmermann.gluappsample.shared.data.BusStop;

import java.util.List;


public class DetailActivity extends ActionBarActivity implements RouteDetailView {

    private Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        presenter = new Presenter(this, GluApiSingleton.getInstance());


        Intent intent = getIntent();

        int routeId = intent.getIntExtra(RouteListActivity.ROUTE_ID, 0);
        String fullDescription = intent.getStringExtra(RouteListActivity.FULL_DESCRIPTION);

        if (routeId != 0) {
            presenter.onDetailRequest(routeId, fullDescription);
        } else {
            backToListView();
        }


    }

    private void backToListView() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setHeader(String description) {
        getHeaderTextView().setText(R.string.detail_stop);
        String baseHeader = getHeaderTextView().getText().toString();
        getHeaderTextView().setText(baseHeader + " " + description);
    }

    @Override
    public void showServiceError() {

    }

    @Override
    public void showDetails(BusDetail result) {

        renderDepartures(result.getDepartures());
        renderStops(result.getStops());
        calculateHeight();

    }

    private void calculateHeight() {
        ListUtils.setDynamicHeight(getStopsList());
        ListUtils.setDynamicHeight(getDeparturesList());
    }

    private void renderStops(List<BusStop> result) {
        ListView stopsView = getStopsList();

        SimpleAdapter<BusStop> adapter = new BusStopSimpleAdapter(result);

        stopsView.setAdapter(adapter);

    }

    private ListView getStopsList() {
        return (ListView) findViewById(R.id.busStopsList);
    }

    private ListView getDeparturesList() {
        return (ListView) findViewById(R.id.busDepartureList);
    }

    private TextView getHeaderTextView() {
        return (TextView) findViewById(R.id.txtHeader);
    }

    private void renderDepartures(List<BusDeparture> result) {
        ListView listView = getDeparturesList();

        SimpleAdapter<BusDeparture> adapter = new BusDepartureSimpleAdapter(result);

        listView.setAdapter(adapter);
    }

    @Override
    public void startProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void stopProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class BusStopSimpleAdapter extends SimpleAdapter<BusStop> {

        public BusStopSimpleAdapter(List<BusStop> result) {
            super(DetailActivity.this, R.layout.list_item, result);
        }

        @Override
        protected String getTextFromData(BusStop objectItem) {
            return new StringBuilder().append(objectItem.getSequence()).append(" - ").append(objectItem.getName()).toString();
        }
    }

    private class BusDepartureSimpleAdapter extends SimpleAdapter<BusDeparture> {

        public BusDepartureSimpleAdapter(List<BusDeparture> result) {
            super(DetailActivity.this, R.layout.list_item, result);
        }

        @Override
        protected String getTextFromData(BusDeparture objectItem) {
            return objectItem.getTime();
        }
    }
}
