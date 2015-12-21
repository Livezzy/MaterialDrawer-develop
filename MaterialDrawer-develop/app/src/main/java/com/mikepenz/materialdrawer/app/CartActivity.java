package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.adapter.CartAdapter;
import com.mikepenz.materialdrawer.app.com.widget.CartItemItem;
import com.mikepenz.materialdrawer.app.comm.ServiceRequest;
import com.mikepenz.materialdrawer.app.contracts.CommUtils;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.core.CartManager;
import com.mikepenz.materialdrawer.app.core.UserManager;
import com.mikepenz.materialdrawer.app.entity.Cart;
import com.mikepenz.materialdrawer.app.entity.CartItem;
import com.mikepenz.materialdrawer.app.entity.GetCartRequest;
import com.mikepenz.materialdrawer.app.entity.GetCartResponse;

import org.json.JSONObject;

public class CartActivity extends BaseActivity {

    private static final String TAG = "CartActivity";
    private CartView _currentView = CartView.Empty;
    private View cartLayout;
    private View emptyCartLayout;
    private CartAdapter _cartAdapter;
    private Button _totalPrice;
    private Cart _cart = CartManager.instance().getCart();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartactivity);

        Log.i(TAG, "onCreate()");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showBackButton();
        setActonBarTitleBar("Cart");

        //cart layout
        cartLayout = findViewById(R.id.cartLayout);
        ListView cartList = (ListView) findViewById(R.id.cartList);
        _cartAdapter = new CartAdapter(this);
        _cartAdapter.setActionHandler(new CartItemItem.IActionHandler() {
            @Override
            public void clearCart(CartItem cartItem) {

            }

            @Override
            public void addCartItem(CartItem cartItem) {

            }

            @Override
            public void removeCartItem(CartItem cartItem) {

            }
        });

        cartList.setAdapter(_cartAdapter);

        findViewById(R.id.proceedToCheckoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.getInstance().isUserLoggedIn(CartActivity.this)) {
                    Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                    intent.putExtra(Constants.BUNDLE_KEY_PROCEED_TO_CHECKOUT, "login first");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(CartActivity.this, CheckOutActivity.class));
                }
            }
        });

        findViewById(R.id.startShopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHomeScreen();
            }
        });

        //empty cart layout
        emptyCartLayout = findViewById(R.id.emptyCartLayout);
        IconicsDrawable cartSymbol = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_shopping_cart)
                .color(getResources().getColor(R.color.silverChalice)).sizeDp(60);
        ((TextView) findViewById(R.id.cartSymbol)).setBackgroundDrawable(cartSymbol);
        _totalPrice = (Button) findViewById(R.id.totalPrice);


        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Log.i(TAG, "wifi IP: " + ip);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart, menu);
        updateMenuIconButton(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (_currentView == CartView.Empty) {
            CartManager.clearCart();
            _cart = null;
            menu.clear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.clear:
                CartManager.clearCart();
                updateView(CartView.Empty);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView(_currentView);
        getCartResponse();
    }

    public void getCartResponse() {
        GetCartRequest object = new GetCartRequest();
        object.setDeviceId(CommUtils.getAndroidDeviceId(getApplicationContext()));

        String url_ajax = Constants.BASE_URL + "/user/getCart";
        JSONObject jsonObject = CommUtils.toJsonObject(object);
        if (jsonObject != null)
            this.volleyRequest(url_ajax, jsonObject, GetCartResponse.class);
    }

    private void volleyRequest(String URL, JSONObject model, final Class<?> instance) {
        showDummyWaitDialog();
        Log.d(TAG, "volleyRequest....:URL " + URL);
        Log.d(TAG, "volleyRequest....:request " + model.toString());
        JsonObjectRequest req = new JsonObjectRequest(URL, model, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                stopProgressDialog();
                Log.d(TAG, "volleyRequest response " + response.toString());
                GetCartResponse getCartResponse = (GetCartResponse) new Gson().fromJson(response.toString(), instance);
                if (getCartResponse != null) {
                    if (getCartResponse.isSuccess) {
                        Cart cart = getCartResponse.getCart();
                        if (cart == null || cart.getCartItems() == null || cart.getCartItems().size() == 0) {
                            updateView(CartView.Empty);
                        } else {
                            _cart = cart;
                            CartManager.saveCart(cart);
                            updateView(CartView.Items);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgressDialog();
                VolleyLog.d(TAG, "volleyRequestCat: error " + error.getMessage());
                showOkDialog("volleyRequestCat ", "Server error!");
            }
        });
        ServiceRequest.getRequestQueue().add(req);
    }

    private void updateView(CartView cartView) {

        Log.i(TAG, "updateView() " + cartView);
        _currentView = cartView;
        emptyCartLayout.setVisibility(_currentView == CartView.Empty ? View.VISIBLE : View.GONE);
        cartLayout.setVisibility(_currentView == CartView.Items ? View.VISIBLE : View.GONE);

        switch (cartView) {
            case Empty:
                invalidateOptionsMenu();
                break;

            case Items:
//                CartItem cartItem = new CartItem();
//                cartItem.setSkuCode("SK04");
//                cartItem.setName("ci.getName()");
//                cartItem.setCategoryId("ci.getCategoryId()");
//                cartItem.setDisplayPrice(new BigDecimal(30));
//                cartItem.setQty(5);
//                cartItem.setNumberOfItems(1);
//
//                List<CartItem> cartItems = new ArrayList<CartItem>();
//                cartItems.add(cartItem);
//                cartItem.setName("ci.name444()");
//                cartItem.setSkuCode("SK04");
//                cartItem.setSkuCode("cat 3");
//                cartItems.add(cartItem);
                _cartAdapter.addAll(_cart.getCartItems());
                _totalPrice.setText(_cart.getTotalCheckoutAmount() + "");
                break;
        }
    }

    public enum CartView {
        Empty, Items
    }

}
