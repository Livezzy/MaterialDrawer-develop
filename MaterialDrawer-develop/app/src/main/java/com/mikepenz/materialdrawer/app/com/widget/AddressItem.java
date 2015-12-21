package com.mikepenz.materialdrawer.app.com.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.entity.UserAddress;


public class AddressItem extends BaseFrameLayout {
    private static final String TAG = "AddressItem";
    private Context context;

    private TextView _addressType;
    private TextView _addressDetails;
    private IActionHandler _iActionHandler;

    private UserAddress _userAddress;

    public AddressItem(Context context) {
        super(context);
        this.context = context;
        initializeUi();
    }

    public void initializeUi() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.addressitem, null);

        _addressType = (TextView) view.findViewById(R.id.addressType);
        _addressDetails = (TextView) view.findViewById(R.id.addressDetails);
        view.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_iActionHandler != null) {
                    _iActionHandler.edit(_userAddress);
                }
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_iActionHandler != null) {
                    //TODO
                    _iActionHandler.selected(_userAddress);
                }
            }
        });

        this.addView(view);
    }

    public void setItem(UserAddress userAddress, IActionHandler iActionHandler) {
        _iActionHandler = iActionHandler;
        Log.i(TAG, "setItem()" + userAddress);
        if (userAddress == null) {
            return;
        }

        this._userAddress = userAddress;
        _addressType.setText(userAddress.getAddressType() + "");
        _addressDetails.setText(userAddress.getAddressType() + "," + userAddress.getStreetAddress() + "," + userAddress.getState());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface IActionHandler {

        public void selected(UserAddress userAddress);

        public void edit(UserAddress userAddress);

    }
}
