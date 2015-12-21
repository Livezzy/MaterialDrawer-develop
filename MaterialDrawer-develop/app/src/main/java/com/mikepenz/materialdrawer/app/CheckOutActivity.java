package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.core.CartManager;
import com.mikepenz.materialdrawer.app.core.UserManager;
import com.mikepenz.materialdrawer.app.entity.Cart;
import com.mikepenz.materialdrawer.app.entity.User;
import com.mikepenz.materialdrawer.app.entity.UserAddress;

public class CheckOutActivity extends BaseActivity {

    private TextView _subTotal;
    private TextView _netPayable;

    private TextView _addressType;
    private TextView _addressDetails;

    private TextView _deliveryTime;
    private User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkoutactivity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showBackButton();
        setActonBarTitleBar("Back");

        IconicsDrawable drawable = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_chevron_right)
                .color(getResources().getColor(R.color.grey)).sizeDp(15);
        ((TextView) findViewById(R.id.rightArrow)).setBackgroundDrawable(drawable);

        _subTotal = (TextView) findViewById(R.id.subTotal);
        _netPayable = (TextView) findViewById(R.id.netPayable);
        _addressType = (TextView) findViewById(R.id.addressType);
        _addressDetails = (TextView) findViewById(R.id.addressDetails);
        _deliveryTime = (TextView) findViewById(R.id.deliveryTime);

        findViewById(R.id.proceedToPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.getInstance().isUserAddressExists()) {
                    Toast.makeText(CheckOutActivity.this, "Enter valid address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(CheckOutActivity.this, "Feature is in progress!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(CheckOutActivity.this,Proceed));
            }
        });

        _user = UserManager.getInstance().getUser();
        if (!UserManager.getInstance().isUserAddressExists()) {
            Intent intent = new Intent(this, AddressActivity.class);
            intent.putExtra(Constants.BUNDLE_KEY_PROCEED_TO_CHECKOUT, "proceed to checkout");
            startActivity(intent);
            return;
        }

        populateUI();

        Cart cart = CartManager.instance().getCart();
        _netPayable.setText(cart.getTotalCheckoutAmount() + "");
        _subTotal.setText(cart.getTotalCheckoutAmount() + "");

    }

    private void populateUI() {
        _user = UserManager.getInstance().getUser();

        UserAddress address = UserManager.getInstance().getDefaultUserAddress();
        if (address != null) {
            (_addressType).setText(address.getAddressType() + "");
            (_addressDetails).setText(address.getStreetAddress() + "," + address.getState());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
