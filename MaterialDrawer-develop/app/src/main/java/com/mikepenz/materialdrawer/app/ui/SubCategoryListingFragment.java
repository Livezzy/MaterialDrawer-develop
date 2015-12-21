package com.mikepenz.materialdrawer.app.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.adapter.SubCategoryListingAdapter;
import com.mikepenz.materialdrawer.app.com.widget.SubCategoryListingItem;
import com.mikepenz.materialdrawer.app.comm.ServiceRequest;
import com.mikepenz.materialdrawer.app.contracts.CommUtils;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.contracts.ValidateUtils;
import com.mikepenz.materialdrawer.app.core.CartManager;
import com.mikepenz.materialdrawer.app.core.UserManager;
import com.mikepenz.materialdrawer.app.entity.AzhanTestController;
import com.mikepenz.materialdrawer.app.entity.Cart;
import com.mikepenz.materialdrawer.app.entity.CartItem;
import com.mikepenz.materialdrawer.app.entity.Dimention;
import com.mikepenz.materialdrawer.app.entity.Item;
import com.mikepenz.materialdrawer.app.entity.SubCategoryResponse;
import com.mikepenz.materialdrawer.app.entity.UpdateCartRequest;
import com.mikepenz.materialdrawer.app.entity.User;
import com.mikepenz.materialdrawer.app.entity.test.Dimentions;
import com.mikepenz.materialdrawer.app.entity.test.Items;
import com.mikepenz.materialdrawer.app.entity.test.SubCategoryListingRequest;
import com.mikepenz.materialdrawer.app.entity.test.SubCategoryListingResponse;
import com.mikepenz.materialdrawer.app.handler.ISelection;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SubCategoryListingFragment extends BaseDialogFragment {
    private static final String TAG = "SubCategoryListingFragment";

    private ISelection<SubCategoryListingAdapter.ListingItem> actionHandler;

    View dataLayout;
    View _emptyLayout;
    private Cart _cart;
    private Button _checkOutAmount;

    private User _user = null;
    private SubCategoryListingAdapter adapter;
    private IActionHandler _iActionHandler;

    @SuppressLint("ValidFragment")
    public SubCategoryListingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.subcategorylisting, container, false);

        rootView.findViewById(R.id.arrow).setBackgroundDrawable(new
                IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_arrow_forward).color(getResources()
                .getColor(R.color.white)));

        rootView.findViewById(R.id.proceedToCheckoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_iActionHandler != null) {
                    _iActionHandler.startCartActivity();
                }
            }
        });
        ListView subCategoryList = (ListView) rootView
                .findViewById(R.id.subCategoryList);
        dataLayout = rootView.findViewById(R.id.dataLayout);
        _emptyLayout = rootView.findViewById(R.id.emptyLayout);

        _checkOutAmount = (Button) rootView.findViewById(R.id.checkOutAmount);
        _user = UserManager.getInstance().getUser();

        SubCategoryResponse subCategoryResponse = AzhanTestController.getTestRes();
        //TODO get above from service....
        _cart = CartManager.instance().getCart();
        Log.i(TAG, "_cart: " + _cart);
        adapter = new SubCategoryListingAdapter(
                getActivity());
        adapter.setIActionHandler(new SubCategoryListingItem.IActionHandler() {
            @Override
            public void updateCartItem(Integer qty) {
                Toast.makeText(getActivity(), "update" + qty, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void addCartItem(SubCategoryListingAdapter.ListingItem listingItem) {
                updateCart(listingItem);
            }

            @Override
            public void removeCartItem(SubCategoryListingAdapter.ListingItem listingItem) {
                updateCheckOutAmount();
            }
        });
        subCategoryList.setAdapter(adapter);

//        List<SubCategoryListingAdapter.ListingItem> listingItems = new ArrayList<>();
//
//        for (Item item : subCategoryResponse.getItems()) {
//            SubCategoryListingAdapter.ListingItem listingItem = adapter.new ListingItem();
//            //TODO update selected total qty of items
//            List<Dimention> dimens = item.getDimentions();
//            listingItem.selectedQty = (dimens != null) ? dimens.size() != 0 ? dimens.get(0).getQty() : 0 : 0;
//            listingItem.item = item;
//            listingItems.add(listingItem);
//        }
//        adapter.addAll(listingItems);

        adapter.setSingleClickHandler(new ISelection<SubCategoryListingAdapter.ListingItem>() {
            @Override
            public void selected(SubCategoryListingAdapter.ListingItem object) {
                actionHandler.selected(object);
            }
        });

//        updateCart();

        subCategoryListingRequest("Hair Care");
        return rootView;
    }

    private void updateCheckOutAmount() {
        _checkOutAmount.setText(_cart.getTotalCheckoutAmount().toString());
    }

    private void updateCart(SubCategoryListingAdapter.ListingItem _listingItem) {


        try {
            UpdateCartRequest cartRequest = new UpdateCartRequest();


            cartRequest.setDeviceId(CommUtils.getAndroidDeviceId(getActivity()));
            if (_user != null) {
                cartRequest.setUserId(_user.getEmailId());
            }

            Dimention dimention = _listingItem.item.getDimention(Integer.valueOf(_listingItem.selectedQty));

            cartRequest.setAdd(_listingItem.isAdd);
            cartRequest.setDelete(_listingItem.isDelete);

            CartItem cartItem = new CartItem();
            cartItem.setSkuCode(dimention.getSkuId());
            cartItem.setName(_listingItem.item.getName());
            cartItem.setCategoryId("");//TODO
            cartItem.setDisplayPrice(dimention.getDisplayPrice());
            cartItem.setQty(dimention.getQty());
            cartItem.setNumberOfItems(1);

            cartRequest.setCartItem(cartItem);

            String url_ajax = Constants.BASE_URL + "/user/saveCart";
            JSONObject jsonObject = CommUtils.toJsonObject(cartRequest);
            if (jsonObject != null) {
                this.volleyRequest(url_ajax, jsonObject, com.mikepenz.materialdrawer.app.entity.Response.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void volleyRequest(String URL, JSONObject model, final Class<?> instance) {
        showDummyWaitDialog();
        Log.d("volleyRequest", model.toString());
        JsonObjectRequest req = new JsonObjectRequest(URL, model, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                stopProgressDialog();
                Log.d(TAG, " volleyRequest " + response.toString());
                com.mikepenz.materialdrawer.app.entity.Response signUpResponse = (com.mikepenz.materialdrawer.app.entity.Response) new Gson().fromJson(response.toString(), instance);
                Log.d(TAG, "volleyRequest....: " + signUpResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgressDialog();
                VolleyLog.d(TAG, "volleyRequestCat: error " + error);
                showOkDialog("volleyRequestCat ", "Server error!");
            }
        });
        ServiceRequest.getRequestQueue().add(req);
    }


    public void subCategoryListingRequest(String subCategoryName) {

        if (!ValidateUtils.isStringValidated(subCategoryName)) {
            Log.d(TAG, "subCategoryListingRequest() provide category!" + subCategoryName);
            return;
        }

        SubCategoryListingRequest object = new SubCategoryListingRequest();
        object.setSubCategoryName(subCategoryName);
        List<String> dealerIDS = new ArrayList<>();
        dealerIDS.add("DL01");
        object.setDealerIDS(dealerIDS.toArray(new String[dealerIDS.size()]));

        String url_ajax = Constants.BASE_URL + "/subCategory/getSubcategory";
        JSONObject jsonObject = CommUtils.toJsonObject(object);
        if (jsonObject != null)
            this.volleyRequestCat(url_ajax, jsonObject, SubCategoryListingResponse.class);
    }

    private void volleyRequestCat(String URL, JSONObject model, final Class<?> instance) {
        showDummyWaitDialog();
        Log.d("volleyRequestCat", model.toString());
        JsonObjectRequest req = new JsonObjectRequest(URL, model, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                stopProgressDialog();
                Log.d(TAG, " volleyRequestCat " + response.toString());
                SubCategoryListingResponse subCategoryListingResponse = (SubCategoryListingResponse) new Gson().fromJson(response.toString(), instance);
                Log.d(TAG, "volleyRequestCat....: " + subCategoryListingResponse);
                if (subCategoryListingResponse != null && subCategoryListingResponse.getIsSuccess().equals("true")) {
                    updateListingUi(subCategoryListingResponse);
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

    private void updateListingUi(SubCategoryListingResponse response) {
        Log.i(TAG, "updateListingUi");

        try {

            Items[] items = new Items[response.getItems().length];
            items = response.getItems();
            if (items == null || items.length == 0) {
                return;
            }

            ArrayList<Item> mItems = new ArrayList<>();
            mItems.clear();
            ;
            for (int index = 0; index < response.getItems().length; index++) {
                Items item = items[index];
                Item i = new Item();
                //TODO add group id field here
                i.setDelearId(item.getDelearId());
                i.setIdealFor(item.getIdealFor());
                i.setName(item.getName());
                i.setComposition(item.getComposition());
                i.setBrand(item.getBrand());
                i.setAvgRating(new BigDecimal(item.getAvgRating()));
                i.setUsedFor(item.getUsedFor());
                i.setType(item.getType());
                i.setRanking(item.getRanking());
                i.setUpc(item.getUpc());

                if (item.getDimentions() == null) {
                    Log.i(TAG, "updateListingUi item.getDimentions()" + item.getDimentions());
                    continue;
                }

                Dimentions dimens[] = new Dimentions[item.getDimentions().length];
                if (dimens == null || dimens.length == 0) {
                    continue;
                }

                dimens = item.getDimentions();

                Log.i(TAG, "updateListingUi  dimens.length: " + dimens.length);
                ArrayList<Dimention> dimentions = new ArrayList<>();
                dimentions.clear();

                for (int k = 0; k < dimens.length; k++) {
                    Dimentions dimention = dimens[k];
                    Dimention d = new Dimention();
                    Log.i(TAG, "updateListingUi  new dimention creating: " + d);

                    d.setUnitsInStock(Integer.valueOf(dimention.getUnitsInStock()));
//                    d.setExpiryDate(dimention.getExpiryDate()); //TODO
                    d.setMrp(Float.valueOf(dimention.getMrp()));
                    d.setQty(Integer.valueOf(dimention.getQty()));
                    d.setOffer(Boolean.valueOf(dimention.getOffer()));
                    d.setDisplayPrice(new BigDecimal(dimention.getDisplayPrice()));
                    d.setDiscount(Float.valueOf(dimention.getDiscount()));
                    d.setUnit(dimention.getUnit());
                    d.setGroupId(dimention.getGroupId());
                    d.setUnitsOnOrder(Integer.valueOf(dimention.getUnitsOnOrder()));
                    d.setHowToUse(dimention.getHowToUse());
                    d.setPictureDescription(dimention.getPictureDescription());
                    d.setDescription(dimention.getDescription());
//                    d.setMfdDate();//TODO
//                    d.setOfferDesc(dimention.getOfferDesc());//TODO
                    d.setSkuId(dimention.getSkuId());

                    i.addDimention(d);
                    Log.i(TAG, "updateListingUi  new dimention creating:i.getDimentions().size() " + i.getDimentions().size());
                }
                mItems.add(i);
            }
//    }
            dataLayout.setVisibility(View.VISIBLE);
            _emptyLayout.setVisibility(View.GONE);

            List<SubCategoryListingAdapter.ListingItem> listingItems = new ArrayList<>();
            for (Item item : mItems) {
                for (Dimention d : item.getDimentions()) {
                    Log.i(TAG, "updateListingUi  d.getSkuId()" + d.getSkuId());
                }
                SubCategoryListingAdapter.ListingItem listingItem = adapter.new ListingItem();
                listingItem.item = item;
                listingItems.add(listingItem);
            }
            adapter.addAll(listingItems);
            Log.i(TAG, "updateListingUi" + listingItems);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog != null) {
            // request a window without the title
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    public void setIActionHandler(IActionHandler iActionHandler) {
        this._iActionHandler = iActionHandler;
    }

    public interface IActionHandler {
        public void startCartActivity();
    }

    public void setActionHandler(ISelection<SubCategoryListingAdapter.ListingItem> actionHandler) {
        this.actionHandler = actionHandler;
    }
}
