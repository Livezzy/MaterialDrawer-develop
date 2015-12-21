package com.mikepenz.materialdrawer.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mikepenz.materialdrawer.app.com.widget.AddressItem;
import com.mikepenz.materialdrawer.app.entity.UserAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DPM on 11/10/2015.
 */
public class AddressAdapter extends BaseAdapter {

    private static final String TAG = "AddressAdapter";
    private Context _context;
    private List<UserAddress> userAddresses = new ArrayList<>();
    private AddressItem.IActionHandler _iActionHandler;

    public AddressAdapter(Context context) {
        _context = context;
    }

    public void addAll(List<UserAddress> userAddresses) {
        if (userAddresses == null || userAddresses.size() == 0) {
            return;
        }
        this.clear();
        for (UserAddress address : userAddresses) {
            this.userAddresses.add(address);
        }
    }

    public void clear() {
        this.userAddresses.clear();
    }

    @Override
    public int getCount() {
        Log.i(TAG, "userAddresses.size(): " + userAddresses.size());
        return userAddresses.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressItem view;

        if (convertView == null) {
            view = new AddressItem(_context);
        } else {
            view = (AddressItem) convertView;
        }
        view.setItem(userAddresses.get(position), _iActionHandler);
        return view;
    }


    public void setActionHandler(AddressItem.IActionHandler actionHandler) {
        _iActionHandler = actionHandler;
    }


}
