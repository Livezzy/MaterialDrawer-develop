package com.mikepenz.materialdrawer.app;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

public class MyOrdersActivity extends BaseActivity {

    private static final String TAG = "MyOrdersActivity";
    private OrderView _currentView = OrderView.Empty;
    private View _ordersLayout;
    private View emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myordersactivity);

        Log.i(TAG, "onCreate()");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        showBackButton();
        setActonBarTitleBar("My Orders");

        emptyLayout = findViewById(R.id.emptyLayout);
        _ordersLayout = findViewById(R.id.orderLayout);

//        ListView cartList = (ListView) findViewById(R.id.orderList);
//        CartAdapter adapter = new CartAdapter(this);
//        cartList.setAdapter(adapter);

        IconicsDrawable cartSymbol = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_shopping_cart)
                .color(getResources().getColor(R.color.silverChalice)).sizeDp(60);
        ((TextView) findViewById(R.id.cartSymbol)).setBackgroundDrawable(cartSymbol);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView(_currentView);
    }

    private void updateView(OrderView cartView) {

        _currentView = cartView;
        emptyLayout.setVisibility(_currentView == OrderView.Empty ? View.VISIBLE : View.GONE);
        _ordersLayout.setVisibility(_currentView == OrderView.Orders ? View.VISIBLE : View.GONE);

        switch (cartView) {
            case Empty:
                invalidateOptionsMenu();
                break;

            case Orders:
                //TODO
                break;
        }
    }

    public enum OrderView {
        Empty, Orders
    }

}
