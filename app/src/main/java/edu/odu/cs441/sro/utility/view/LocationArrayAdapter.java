package edu.odu.cs441.sro.utility.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.metadata.Location;

public class LocationArrayAdapter extends ArrayAdapter<Location> {

    private Context context;
    private List<Location> locations;

    public LocationArrayAdapter(@NonNull Context context, List<Location> locations) {
        super(context, android.R.layout.simple_list_item_1, locations);
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (listItem == null) {
            listItem = mInflater.inflate(R.layout.custom_spinner_item, null);
        }

        Location location = locations.get(position);

        TextView locationTextView = listItem.findViewById(R.id.custom_spinner_item_textview);
        locationTextView.setText(location.getLocation());

        return listItem;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (listItem == null) {
            listItem = mInflater.inflate(R.layout.custom_spinner_item, null);
        }

        Location location = locations.get(position);

        TextView locationTextView = listItem.findViewById(R.id.custom_spinner_item_textview);
        locationTextView.setText(location.getLocation());

        return listItem;
    }
}
