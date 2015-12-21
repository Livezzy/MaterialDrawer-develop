package com.mikepenz.materialdrawer.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mikepenz.materialdrawer.app.com.widget.SubCategoryListingItem;
import com.mikepenz.materialdrawer.app.entity.Item;
import com.mikepenz.materialdrawer.app.handler.ISelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SubCategoryListingAdapter extends BaseAdapter {

    private Context _context;

    private ISelection<ListingItem> _handler;
    private List<ListingItem> items = new ArrayList<ListingItem>();
    private List<ListingItem> itemsForSearch = new ArrayList<ListingItem>();

    private SubCategoryListingItem.IActionHandler _iActionHandler;

    public SubCategoryListingAdapter(Context context) {
        this._context = context;
    }

    public void clearAll() {
        if (items != null && items.size() > 0) {
            items.clear();
            itemsForSearch.clear();
            this.notifyDataSetChanged();
        }
    }

    public void addAll(List<ListingItem> listingItems) {
        if (listingItems != null && listingItems.size() > 0) {
            this.items.clear();
            this.itemsForSearch.clear();
            for (ListingItem listingItem : listingItems) {
                this.items.add(listingItem);
                this.itemsForSearch.add(listingItem);

                // TODO test

            }
            this.notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        if (items != null && items.size() > 0)
            return items.size();
        else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubCategoryListingItem view;

        if (convertView == null) {
            view = new SubCategoryListingItem(_context, _handler);
        } else {
            view = (SubCategoryListingItem) convertView;
        }
        view.setItem(items.get(position), _iActionHandler);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(itemsForSearch);
        } else {
            for (ListingItem wp : itemsForSearch) {
//				if (wp.name.toLowerCase(Locale.getDefault()).contains(charText)) {
//					items.add(wp);
//				}
            }
        }
        notifyDataSetChanged();
    }

    public void setSingleClickHandler(ISelection<ListingItem> iSelection) {
        this._handler = iSelection;
    }

    public void setIActionHandler(SubCategoryListingItem.IActionHandler iActionHandler) {
        _iActionHandler = iActionHandler;
    }

    public class ListingItem {
        public Item item;
        public Integer numberOfItems = 0;
        public Integer selectedQty;
        public boolean isFavorite;
        public boolean isAdd;
        public boolean isDelete;
    }
}
