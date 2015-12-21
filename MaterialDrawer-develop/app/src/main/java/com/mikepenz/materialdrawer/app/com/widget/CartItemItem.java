package com.mikepenz.materialdrawer.app.com.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.adapter.SubCategoryListingAdapter;
import com.mikepenz.materialdrawer.app.entity.Cart;
import com.mikepenz.materialdrawer.app.entity.CartItem;

import java.math.BigDecimal;


public class CartItemItem extends BaseFrameLayout {
    private static final String TAG = "CartItemItem";
    private Context context;

    private TextView _name;
    private TextView _qty;
    private TextView _price;
    private TextView _totalNumberOfItems;
    private TextView _headerName;
    private TextView _headerTotalAmount;
    private IActionHandler _iActionHandler;

    private CartItem _cartItem;

    public CartItemItem(Context context) {
        super(context);
        this.context = context;
        initializeUi();
    }

    public void initializeUi() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cartitemitem, null);
        IconicsDrawable iconicsDrawable = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_close).sizeDp(7)
                .color(context.getResources().getColor(R.color.drawerTextColor));
        ((TextView) view.findViewById(R.id.clear)).setBackgroundDrawable(iconicsDrawable);

        _name = (TextView) view.findViewById(R.id.name);
        _qty = (TextView) view.findViewById(R.id.qty);
        _price = (TextView) view.findViewById(R.id.price);
        _totalNumberOfItems = (TextView) view.findViewById(R.id.qty);

        _headerName = (TextView) view.findViewById(R.id.headerName);
        _headerTotalAmount = (TextView) view.findViewById(R.id.headerTotals);


        Button _addItem = (Button) view.findViewById(R.id.addItem);
        Button _removeItem = (Button) view.findViewById(R.id.removeItem);

        _addItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_iActionHandler != null) {
                    _iActionHandler.addCartItem(_cartItem);
                }
            }
        });

        _removeItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_iActionHandler != null) {
                    _iActionHandler.removeCartItem(_cartItem);
                }
            }
        });

        view.findViewById(R.id.clear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_iActionHandler != null) {
                    _iActionHandler.clearCart(_cartItem);
                }
            }
        });

        this.addView(view);
    }

    public void setItem(CartItem cartItem, IActionHandler iActionHandler) {
        Log.i(TAG, "setItem()" + cartItem);
        if (cartItem != null) {
            this._cartItem = cartItem;
            _iActionHandler = iActionHandler;
            _name.setText(cartItem.getName());
            _qty.setText(cartItem.getQty() + "");
            _price.setText(cartItem.getDisplayPrice() + "");
            _totalNumberOfItems.setText("" + cartItem.getNumberOfItems());
            _headerName.setText(cartItem.getCategoryId());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface IActionHandler {

        public void clearCart(CartItem cartItem);

        public void addCartItem(CartItem cartItem);

        public void removeCartItem(CartItem cartItem);
    }
}
