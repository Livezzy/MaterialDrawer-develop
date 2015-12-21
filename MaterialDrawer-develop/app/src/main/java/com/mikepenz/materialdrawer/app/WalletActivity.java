package com.mikepenz.materialdrawer.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.mikepenz.materialdrawer.app.adapter.AddressAdapter;
import com.mikepenz.materialdrawer.app.entity.UserAddress;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends BaseActivity {
    private static final String TAG = "WalletActivity";

  //  private AddressFunction _currentView = AddressFunction.AddressInput;

    private View _addressInputLayout;
    private View addressListingLayout;
    private ListView _addressList;

    private AddressAdapter _addressAdapter;

    private List<UserAddress> _userAddresses = new ArrayList<>();
    private UserAddress _userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        showBackButton();
        setActonBarTitleBar("Wallet");
    }
}
//        _addressInputLayout = findViewById(R.id.addressInputLayout);
//        addressListingLayout = findViewById(R.id.addressListingLayout);
//        findViewById(R.id.addAddress).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateView(AddressFunction.AddressInput);
//            }
//        });
//
//        _addressList = (ListView) findViewById(R.id.addressList);
//        _addressAdapter = new AddressAdapter(this);
//        _addressAdapter.setActionHandler(new AddressItem.IActionHandler() {
//            @Override
//            public void selected(UserAddress userAddress) {
//                if (_isProceedToCheckOut) {
//                    finish();
//                }
//            }
//
//            @Override
//            public void edit(UserAddress userAddress) {
//                _userAddress = userAddress;
//                updateView(AddressFunction.AddressInput);
//            }
//        });
//        _addressList.setAdapter(_addressAdapter);
//
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.address, menu);
//        updateMenuIconButton(menu);
//        return true;
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
////        if (_currentView == AddressFunction.AddressListing) {
////            menu.clear();
////        }
//        return super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            case R.id.save:
//                if (_currentView == AddressFunction.AddressListing) {
//                    return true;
//                }
//                saveAddress();
//                return true;
//            default:
//                return true;
//        }
//    }

//    private void saveAddress() {
//
//        int cheakedRadioButton = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
//        String lable = ((RadioButton) findViewById(cheakedRadioButton)).getText().toString();
//        String name = ((EditText) findViewById(R.id.user_name)).getText().toString();
//        String flat_house_office = ((EditText) findViewById(R.id.flat_house_office)).getText().toString();
//        String street_socity = ((EditText) findViewById(R.id.street_socity)).getText().toString();
//        String address_details = ((EditText) findViewById(R.id.address_details)).getText().toString();
//        boolean isDefaultAddress = ((CheckBox) findViewById(R.id.setAsDefault)).isChecked();
//
//        User user = UserManager.getInstance().getUser();
//        if (user == null) {
//            Log.e(TAG, "saveAddress() user " + user);
//            return;
//        }
//
//        List<UserAddress> userAddresses = new ArrayList<>();
//        UserAddress userAddress = new UserAddress();
//        if (UserManager.getInstance().isUserAddressExists()) {
//            userAddresses = user.getUserAddress();
//            Log.i(TAG, "saveAddress() existing address _userAddresses " + userAddresses);
//        }
//
//        Log.i(TAG, "saveAddress() _userAddresses " + userAddresses);
//
//        userAddress.setAddressType(lable);
//        userAddress.setName(name);
////        userAddress.setFlatNo(flat_house_office);
//        userAddress.setStreetAddress(street_socity);
//        userAddress.setState(address_details);
//        userAddress.setDefaultAddress(isDefaultAddress);
//
//        userAddresses.add(userAddress);
//        user.setUserAddress(userAddresses);
//
//        //service request
//        updateProfileRequest updateProfileRequest = new updateProfileRequest();
//        updateProfileRequest.setUser(user);
//
//        this.updateProfile(updateProfileRequest);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (UserManager.getInstance().isUserAddressExists()) {
//            _userAddresses = UserManager.getInstance().getUser().getUserAddress();
//            _currentView = AddressFunction.AddressListing;
//        }
//        updateView(_currentView);
//    }
//
//    private void updateView(AddressFunction cartView) {
//
//        _currentView = cartView;
//        addressListingLayout.setVisibility(_currentView == AddressFunction.AddressListing ? View.VISIBLE : View.GONE);
//        _addressInputLayout.setVisibility(_currentView == AddressFunction.AddressInput ? View.VISIBLE : View.GONE);
//
//        switch (cartView) {
//            case AddressListing:
//                invalidateOptionsMenu();
//                updateListingUi();
//                break;
//
//            case AddressInput:
//                updateInputLayout();
//                break;
//        }
//    }
//
//    public void updateListingUi() {
//        _addressAdapter.addAll(_userAddresses);
//    }
//
//    public void updateInputLayout() {
//
//        if (_userAddress == null) {
//            return;
//        }
//
//        setCheakedRadioButton(_userAddress.getAddressType());
//        ((EditText) findViewById(R.id.user_name)).setText(_userAddress.getName());
//        ((EditText) findViewById(R.id.flat_house_office)).setText(_userAddress.getCity());
//        ((EditText) findViewById(R.id.street_socity)).setText(_userAddress.getStreetAddress());
//        ((EditText) findViewById(R.id.address_details)).setText(_userAddress.getState());
//    }
//
//    private void setCheakedRadioButton(String checkedLable) {
//        RadioButton r1 = (RadioButton) findViewById(R.id.radioButton);
//        RadioButton r2 = (RadioButton) findViewById(R.id.radioButton2);
//        RadioButton r3 = (RadioButton) findViewById(R.id.radioButton3);
//        if (checkedLable.equalsIgnoreCase(r1.getText().toString()))
//            r1.setChecked(true);
//        else if (checkedLable.equalsIgnoreCase(r2.getText().toString()))
//            r2.setChecked(true);
//        else if (checkedLable.equalsIgnoreCase(r3.getText().toString()))
//            r3.setChecked(true);
//
//
//    }
//
//    public void updateProfile(updateProfileRequest object) {
//        String url_ajax = Constants.BASE_URL + "/user/updateProfile";
//        JSONObject jsonObject = CommUtils.toJsonObject(object);
//        if (jsonObject != null) {
//            this.volleyRequest(url_ajax, jsonObject, UpdateUserProfileResponse.class);
//        }
//    }
//
//    private void volleyRequest(String URL, JSONObject model, final Class<?> instance) {
//        showDummyWaitDialog();
//        Log.d("TAG", "volleyRequest request " + model.toString());
//        Log.d("TAG", "volleyRequest URL" + URL);
//
//        JsonObjectRequest req = new JsonObjectRequest(URL, model, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("TAG", "volleyRequest response: " + response);
//                stopProgressDialog();
//                Log.d(TAG, response.toString());
//                UpdateUserProfileResponse updateProfileResponse = (UpdateUserProfileResponse) new Gson().fromJson(response.toString(), instance);
//                if (updateProfileResponse.isSuccess) {
//                    UserManager.getInstance().saveUser(updateProfileResponse.getUser(), WalletActivity.this);
//                    if (_isProceedToCheckOut) {
//                        finish();
//                    } else {
//                        WalletActivity.this.updateView(AddressFunction.AddressListing);
//                    }
//                }
//
//                showOkDialog("Save address error!", updateProfileResponse.description);
//                Log.d(TAG, "volleyRequest....: " + updateProfileResponse.isSuccess);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                stopProgressDialog();
//                VolleyLog.d(TAG, "volleyRequest error: " + error.getMessage());
//                showOkDialog("Login Error!", "Server error!");
//            }
//        });
//        ServiceRequest.getRequestQueue().add(req);
//    }
//
//    public enum AddressFunction {
//        AddressListing, AddressInput
//    }
//}
