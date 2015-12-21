package com.mikepenz.materialdrawer.app.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.R;

@SuppressLint("ValidFragment")
public class PurchaseItemFragment extends BaseDialogFragment {
    @SuppressLint("ValidFragment")
    public PurchaseItemFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.purchaseitemfragment, container, false);

        final IconicsDrawable icon = new IconicsDrawable(getActivity())
                .icon(GoogleMaterial.Icon.gmd_label)
                .color(getResources().getColor(R.color.menu_bar));
        ((TextView) rootView.findViewById(R.id.offerLblIcon)).setBackgroundDrawable(icon);

        return rootView;
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

}
