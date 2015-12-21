package com.mikepenz.materialdrawer.app.entity.test;

/**
 * Created by HP on 08-12-2015.
 */
public class SubCategoryListingRequest {
    private String[] dealerIDS;

    private String subCategoryName;

    public String[] getDealerIDS() {
        return dealerIDS;
    }

    public void setDealerIDS(String[] dealerIDS) {
        this.dealerIDS = dealerIDS;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    @Override
    public String toString() {
        return "ClassPojo [dealerIDS = " + dealerIDS + ", subCategoryName = " + subCategoryName + "]";
    }
}
