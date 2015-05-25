package com.github.andrezimmermann.gluappsample.client.widgets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.andrezimmermann.gluappsample.R;
import com.github.andrezimmermann.gluappsample.shared.data.IdentityObject;

import java.util.LinkedList;
import java.util.List;

public abstract class SimpleAdapter<T extends IdentityObject> extends ArrayAdapter<T> {

    private int resourceId;
    //Need as ordered
    private List<T> dataList;


    private LayoutInflater layoutInflater;

    public SimpleAdapter(Context context, int resourceId,
                         List<T> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dataList = new LinkedList<>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view;
        TextView text;

        if (convertView == null) {
            view = layoutInflater.inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }

        try {

            //  Otherwise, find the TextView field within the layout
            text = (TextView) view.findViewById(R.id.textViewItem);

        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        // object item based on the position
        T objectItem = dataList.get(position);

        // get the TextView and then set the text (item name) and tag (item ID) values

        text.setText(getTextFromData(objectItem));
        text.setTag(objectItem.getId());

        return view;

    }

    protected abstract String getTextFromData(T objectItem);

    @Override
    public long getItemId(int position) {
        T item = getItem(position);
        return item.getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}