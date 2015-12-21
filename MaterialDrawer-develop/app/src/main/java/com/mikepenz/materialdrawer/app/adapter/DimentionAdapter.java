package com.mikepenz.materialdrawer.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikepenz.materialdrawer.app.R;

import java.util.List;

public class DimentionAdapter extends ArrayAdapter<String> {

    private Context _context;
    private String[] objects;

    public DimentionAdapter(Context context, int textViewResourceId,
                            String[] objects) {
        super(context, textViewResourceId, objects);
        _context = context;
        this.objects = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO implement holder pattern here
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dimentionrow, parent, false);
        TextView label = (TextView) row.findViewById(R.id.lbl);
        //TODO
        label.setText(objects[position] + "ML");

        return row;
    }
}