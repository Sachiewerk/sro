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
import edu.odu.cs441.sro.entity.metadata.Method;

public class MethodArrayAdapter extends ArrayAdapter<Method> {

    private Context context;
    private List<Method> methods;

    public MethodArrayAdapter(@NonNull Context context, List<Method> methods) {
        super(context, 0, methods);
        this.context = context;
        this.methods = methods;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = mInflater.inflate(R.layout.custom_spinner_item, null);

        Method method = methods.get(position);

        TextView methodTextView = view.findViewById(R.id.custom_spinner_item_textview);
        methodTextView.setText(method.getMethod());

        return view;
    }
}
