package com.mikepenz.materialdrawer.app.com.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.adapter.DimentionAdapter;
import com.mikepenz.materialdrawer.app.adapter.SubCategoryListingAdapter;
import com.mikepenz.materialdrawer.app.contracts.ValidateUtils;
import com.mikepenz.materialdrawer.app.core.CartManager;
import com.mikepenz.materialdrawer.app.entity.Cart;
import com.mikepenz.materialdrawer.app.handler.ISelection;

import java.util.List;


public class SubCategoryListingItem extends BaseFrameLayout {
    private static final String TAG = "SubCategoryListingItem";

    private Context context;
    private TextView _name;
    private TextView _size;
    private TextView _price;

    private TextView _numberOfItems;

    private ISelection<SubCategoryListingAdapter.ListingItem> _handler;

    private Button _addItem;
    private Button _removeItem;

    private SubCategoryListingAdapter.ListingItem _listingItem;
    private Cart _cart = CartManager.instance().getCart();

    IActionHandler _iActionHandler;
    private Spinner dynamicSpinner;

    public SubCategoryListingItem(Context context, ISelection<SubCategoryListingAdapter.ListingItem> handler) {
        super(context);
        this.context = context;
        _handler = handler;
        initializeUi();
    }

    public void initializeUi() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subcategorylistingitem, null);
        this.addView(view);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (_handler != null) {
                    _handler.selected(_listingItem);
                }
            }
        });
        _name = (TextView) this.findViewById(R.id.name);
        _numberOfItems = (TextView) this.findViewById(R.id.qty);
        _addItem = (Button) this.findViewById(R.id.addItem);
        _removeItem = (Button) this.findViewById(R.id.removeItem);

        dynamicSpinner = (Spinner) findViewById(R.id.dimention);

        _addItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_iActionHandler != null) {
                    _listingItem.numberOfItems++;
                    updateNumberOfItemText(_listingItem.numberOfItems);
                    if (_iActionHandler != null) {
                        _listingItem.selectedQty = Integer.valueOf(dynamicSpinner.getSelectedItem().toString());
                        _listingItem.isAdd = true;
                        _iActionHandler.addCartItem(_listingItem);
                    }
                }
            }
        });

        _removeItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_listingItem.numberOfItems > 0) {
                    _listingItem.numberOfItems--;
                    updateNumberOfItemText(_listingItem.numberOfItems);
                    if (_iActionHandler != null) {
                        _listingItem.selectedQty = Integer.valueOf(dynamicSpinner.getSelectedItem().toString());
                        _listingItem.isAdd = false;
                        _iActionHandler.removeCartItem(_listingItem);
                    }
                }
            }
        });

        int size = 15;
        final IconicsDrawable fav = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_favorite)
                .color(getResources().getColor(R.color.red)).sizeDp(size);

        final IconicsDrawable unFav = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_favorite_outline)
                .color(getResources().getColor(R.color.drawerTextColor)).sizeDp(size);

        final ToggleButton favUnFav = (ToggleButton) view.findViewById(R.id.favUnfav);
        favUnFav.setBackgroundDrawable(fav);
        favUnFav.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (favUnFav.isChecked()) {
                    //Button is ON
                    favUnFav.setBackgroundDrawable(fav);
                    // Do Something
                } else {
                    favUnFav.setBackgroundDrawable(unFav);
                }
                //Button is OFF
                // Do Something
            }
        });

        final IconicsDrawable offerLblIcon = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_label)
                .color(getResources().getColor(R.color.menu_bar)).sizeDp(10);
        ((TextView) findViewById(R.id.offerIcon)).setBackgroundDrawable(offerLblIcon);
    }


    public void setItem(SubCategoryListingAdapter.ListingItem item, IActionHandler iActionHandler) {
        _iActionHandler = iActionHandler;
        this._listingItem = item;
        if (_listingItem != null && _listingItem.item != null) {
            _name.setText(_listingItem.item.getName());
            updateNumberOfItemText(_listingItem.numberOfItems);

            List<String> qtys = _listingItem.item.getQtyAsList();
            if (qtys != null && qtys.size() > 0) {
                dynamicSpinner.setVisibility(View.VISIBLE);
                Log.i(TAG, "dimens size on spinner " + qtys.size());

                String ar[] = new String[qtys.size()];
                for (int i = 0; i < qtys.size(); i++) {
                    String str = qtys.get(i);
                    if (ValidateUtils.isStringValidated(str)) {
                        ar[i] = str;
                    }
                }

                Log.i(TAG, "dimens size on spinner ar lent: " + ar.length);

                dynamicSpinner.setAdapter(new DimentionAdapter(context, R.layout.dimentionrow, ar));

                dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {

                        String selectedValue = dynamicSpinner.getSelectedItem().toString();
                        Log.v(TAG, "(String) parent.getItemAtPosition(position): " + (String) parent.getItemAtPosition(position) + "selectedValue: " + selectedValue);
                        Log.i(TAG, " selected sku code " + _listingItem.item.getItemSkuCode(Integer.valueOf(selectedValue)));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            } else {
                dynamicSpinner.setVisibility(View.INVISIBLE);
            }
        }
    }

    private String getSkuCodeFromSpinner() {
        return _listingItem.item.getItemSkuCode(Integer.valueOf(dynamicSpinner.getSelectedItem().toString()))
                ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void updateNumberOfItemText(Integer numberOfItems) {
        _numberOfItems.setText("" + numberOfItems);
    }

    public void setActionHandler(IActionHandler actionHandler) {
        _iActionHandler = actionHandler;
    }

    public interface IActionHandler {
        public void updateCartItem(Integer qty);

        public void addCartItem(SubCategoryListingAdapter.ListingItem listingItem);

        public void removeCartItem(SubCategoryListingAdapter.ListingItem listingItem);
    }
}
