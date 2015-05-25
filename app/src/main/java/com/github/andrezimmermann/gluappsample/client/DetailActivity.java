package com.github.andrezimmermann.gluappsample.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.andrezimmermann.gluappsample.R;
import com.github.andrezimmermann.gluappsample.client.detail.Presenter;
import com.github.andrezimmermann.gluappsample.client.detail.RouteDetailView;
import com.github.andrezimmermann.gluappsample.client.service.BusDetail;
import com.github.andrezimmermann.gluappsample.client.service.GluApiSingleton;
import com.github.andrezimmermann.gluappsample.client.widgets.SimpleAdapter;
import com.github.andrezimmermann.gluappsample.shared.data.BusDeparture;
import com.github.andrezimmermann.gluappsample.shared.data.BusStop;

import java.util.List;


public class DetailActivity extends ActionBarActivity implements RouteDetailView {

    public static final int EMPTY_ROUTE_ID = -1;

    private Presenter presenter;
    private ProgressDialog progressDialog;

    private int routeId = EMPTY_ROUTE_ID;


    private ViewFlipper viewFlipper;
    private float lastX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        presenter = new Presenter(this, GluApiSingleton.getInstance());

        viewFlipper = (ViewFlipper) findViewById(R.id.detail_flipper);

        Intent intent = getIntent();

        int routeId = intent.getIntExtra(RouteListActivity.ROUTE_ID, 0);
        String fullDescription = intent.getStringExtra(RouteListActivity.FULL_DESCRIPTION);

        if (shouldFetchDetail(routeId, this.routeId)) {
            processRouteId(routeId, fullDescription);
        }

    }

    // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN: {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX) {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(this, R.anim.in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                    // Show the next Screen
                    viewFlipper.showNext();
                }

                // if right to left swipe on screen
                if (lastX > currentX) {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(this, R.anim.in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                    // Show The Previous Screen
                    viewFlipper.showPrevious();
                }
                break;
            }
        }
        return false;
    }

    private boolean shouldFetchDetail(int currentRouteId, int oldRouteId) {
        return currentRouteId != oldRouteId;
    }

    private void processRouteId(int routeId, String fullDescription) {
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
