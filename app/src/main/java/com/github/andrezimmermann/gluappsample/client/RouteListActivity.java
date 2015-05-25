package com.github.andrezimmermann.gluappsample.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.andrezimmermann.gluappsample.R;
import com.github.andrezimmermann.gluappsample.client.list.ListPresenter;
import com.github.andrezimmermann.gluappsample.client.list.RouteListView;
import com.github.andrezimmermann.gluappsample.client.mvp.event.ClickHandler;
import com.github.andrezimmermann.gluappsample.client.mvp.event.HasClickHandlers;
import com.github.andrezimmermann.gluappsample.client.mvp.event.HasListItemSelectionHandlers;
import com.github.andrezimmermann.gluappsample.client.mvp.event.ListItemSelectionHandler;
import com.github.andrezimmermann.gluappsample.client.service.GluApiSingleton;
import com.github.andrezimmermann.gluappsample.client.widgets.SimpleAdapter;
import com.github.andrezimmermann.gluappsample.server.api.GluApi;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;

import java.util.List;


@SuppressWarnings("deprecation")
public class RouteListActivity extends ActionBarActivity implements RouteListView {


    public static final String ROUTE_ID = "com.github.andrezimmermann.gluappsample.client.RouteListActivity.ROUTE_ID";
    public static final String FULL_DESCRIPTION = "com.github.andrezimmermann.gluappsample.client.RouteListActivity.FULL_DESCRIPTION";

    private ProgressDialog progressDialog;
    private ListPresenter presenter;
    private GluApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        service = GluApiSingleton.getInstance();

        presenter = new ListPresenter(this, service);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
    public HasClickHandlers getQueryButtonClick() {
        return new HasClickHandlers() {
            @Override
            public void addClickHandler(final ClickHandler handler) {
                View.OnClickListener listener = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        handler.onClick();
                    }
                };

                Button btQuery = getButtonQuery();
                btQuery.setOnClickListener(listener);
            }
        };
    }

    private Button getButtonQuery() {
        return (Button) findViewById(R.id.btQuery);
    }

    @Override
    public void setQueryBlankError() {
        getQueryInput().setError(getString(R.string.list_query_empty_error));
    }

    @Override
    public String getQueryInputText() {
        return getQueryInput().getText().toString();
    }

    private EditText getQueryInput() {
        return (EditText) findViewById(R.id.txtQuery);
    }

    @Override
    public void setServiceError() {
        Toast.makeText(getApplicationContext(), "Service Unavailable, try again later!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showResult(List<BusLine> busLines) {
        ListView busListView = getBusRouteList();


        SimpleAdapter<BusLine> adapter = new BusLineSimpleAdapter(busLines);

        busListView.setAdapter(adapter);


    }

    @Override
    public HasListItemSelectionHandlers getBusListSelecion() {
        HasListItemSelectionHandlers handlers = new HasListItemSelectionHandlers() {
            @Override
            public void addListItemSelecionHandler(final ListItemSelectionHandler handler) {

                getBusRouteList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BusLine selectedItem = (BusLine) parent.getItemAtPosition(position);

                        handler.onListSelecion(selectedItem);
                    }
                });

            }
        };
        return handlers;
    }

    @Override
    public void showDetails(BusLine data) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(ROUTE_ID, data.getId());

        String fullDescription = new StringBuilder().append(data.getNumberId()).append(" - ").append(data.getDescription()).toString();
        intent.putExtra(FULL_DESCRIPTION,
                fullDescription);

        startActivity(intent);
    }

    private ListView getBusRouteList() {
        return (ListView) findViewById(R.id.busRouteList);
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

    private class BusLineSimpleAdapter extends SimpleAdapter<BusLine> {

        public BusLineSimpleAdapter(List<BusLine> busLines) {
            super(RouteListActivity.this, R.layout.list_item, busLines);
        }

        @Override
        protected String getTextFromData(BusLine objectItem) {
            return new StringBuilder().append(objectItem.getNumberId()).append(" - ").append(objectItem.getDescription()).toString();
        }
    }
}
