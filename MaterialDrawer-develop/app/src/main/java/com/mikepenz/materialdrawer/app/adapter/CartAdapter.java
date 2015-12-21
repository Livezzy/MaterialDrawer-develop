package com.mikepenz.materialdrawer.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mikepenz.materialdrawer.app.com.widget.CartItemItem;
import com.mikepenz.materialdrawer.app.entity.Cart;
import com.mikepenz.materialdrawer.app.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DPM on 11/10/2015.
 */
public class CartAdapter extends BaseAdapter {

    private static final String TAG = "CartAdapter";
    private Context _context;
    private List<CartItem> _cartItems = new ArrayList<>();
    private CartItemItem.IActionHandler _iActionHandler;

    public CartAdapter(Context context) {
        _context = context;
    }

    public void addAll(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.size() == 0) {
            return;
        }
        this.clear();
        for (CartItem cartItem : cartItems) {
            this._cartItems.add(cartItem);
        }
    }

    public void clear() {
        this._cartItems.clear();
    }

    @Override
    public int getCount() {
        Log.i(TAG, "_cartItems.size(): " + _cartItems.size());
        return _cartItems.size();
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
        CartItemItem view;

        if (convertView == null) {
            view = new CartItemItem(_context);
        } else {
            view = (CartItemItem) convertView;
        }
        view.setItem(_cartItems.get(position), _iActionHandler);
        return view;
    }


    public void setActionHandler(CartItemItem.IActionHandler actionHandler) {
        _iActionHandler = actionHandler;
    }


}
