package com.mikepenz.materialdrawer.app.entity;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Item {

    private static final String TAG = "Item";
    private String delearId;
    private String upc;
    private String categoryId;

    private String name;
    private String type;
    private String brand;
    private String usedFor;
    private String idealFor;
    private String composition;
    private String ranking;
    private BigDecimal avgRating;

    private List<Dimention> dimentions;

    public String getComposition() {

        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(BigDecimal avgRating) {
        this.avgRating = avgRating;
    }


    public String getDelearId() {
        return delearId;
    }

    public void setDelearId(String delearId) {
        this.delearId = delearId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUsedFor() {
        return usedFor;
    }

    public void setUsedFor(String usedFor) {
        this.usedFor = usedFor;
    }

    public String getIdealFor() {
        return idealFor;
    }

    public void setIdealFor(String idealFor) {
        this.idealFor = idealFor;
    }

    public List<Dimention> getDimentions() {
        return dimentions;
    }

    public void setDimentions(List<Dimention> dimentions) {
        this.dimentions = dimentions;
    }

//	public BigDecimal getItemPrice

    public void addDimention(Dimention dimen) {
        if (this.dimentions == null) {
            this.dimentions = new ArrayList<Dimention>();
        }
        this.dimentions.add(dimen);
    }

    public String getItemSkuCode(BigDecimal price) {
        for (Dimention dimention : this.getDimentions()) {
            if (dimention.getDisplayPrice().equals(price)) {
                return dimention.getSkuId();
            }
        }
        return null;
    }

    public BigDecimal getItemDisplayPrice(String skuCode) {
        for (Dimention dimention : this.getDimentions()) {
            if (dimention.getSkuId().equals(skuCode)) {
                return dimention.getDisplayPrice();
            }
        }
        return null;
    }

    public String getItemSkuCode(Integer qty) {
        for (Dimention dimention : this.getDimentions()) {
            if (dimention.getQty().equals(qty)) {
                return dimention.getSkuId();
            }
        }
        return null;
    }

    public Dimention getDimention(Integer qty) {

        if (this.dimentions == null || this.dimentions.size() == 0) {
            return null;
        }

        for (Dimention dimention : this.getDimentions()) {
            if (dimention.getQty().equals(qty)) {
                return dimention;
            }
        }

        return null;
    }

    public ArrayList<String> getQtyAsList() {
        ArrayList<String> list = new ArrayList<>();
        if (this.dimentions == null || this.dimentions.size() == 0) {
            return list;
        }
        for (Dimention dimention : this.dimentions) {
            Log.i(TAG, "dimention:" + dimention + " " + dimention.getQty().toString());
            list.add(dimention.getQty().toString());
        }
        return list;
    }
}
