package com.mikepenz.materialdrawer.app.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AzhanTestController {

    public static SubCategoryResponse getTestRes() {

        SubCategoryResponse response = new SubCategoryResponse();
        response.isSuccess = true;
        response.description = null;

        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setDelearId("DL01");
        item.setUpc("12345678999");
        item.setCategoryId("100");
        item.setName("Hair Fall Rescue");
        item.setType("Shampoo");
        item.setBrand("Dove");
        item.setUsedFor("Anti Hair Fall");
        item.setIdealFor("Women");
        item.setComposition("1/4th Moisturizing Cream, Sodium Cocoyl Isethionate, Stearic Acid, Sodium Palmitate, Water, Lauric Acid, Perfume, Glycerin");
        item.setRanking("45");
        item.setAvgRating(new BigDecimal(4.5));

        List<Dimention> dimentions = new ArrayList<Dimention>();
        Dimention dimention = new Dimention();
        dimention.setSkuId("SK1");
        dimention.setGroupId("Dove0001");
        dimention.setMrp(160f);
        dimention.setDisplayPrice(new BigDecimal(10));
        dimention.setDiscount(10);
        dimention.setUnit("ML");
        dimention.setQty(100);
        dimention
                .setDescription("For soft, smooth and glowing skin Rich blend that moisturizes and hydrates Number one dermatologist recommend");
        dimention.setOffer(true);
        dimention.setOfferDesc("Buy 1 get 1");
        dimention.setUnitsOnOrder(1);
        dimention.setUnitsInStock(250);
        dimention.setMfdDate(new Date());
        dimention.setExpiryDate(new Date());

        dimentions.add(dimention);

        dimention = new Dimention();
        dimention.setSkuId("SK2");
        dimention.setGroupId("Dove0002");
        dimention.setMrp(160f);
        dimention.setDisplayPrice(new BigDecimal(20));
        dimention.setDiscount(10);
        dimention.setUnit("ML");
        dimention.setQty(300);
        dimention
                .setDescription("DESC that moisturizes and hydrates Number one dermatologist recommend");
        dimention.setOffer(true);
        dimention.setOfferDesc("Buy 1 get 1");
        dimention.setUnitsOnOrder(1);
        dimention.setUnitsInStock(250);
        dimention.setMfdDate(new Date());
        dimention.setExpiryDate(new Date());

        dimentions.add(dimention);

        dimention = new Dimention();
        dimention.setSkuId("SK4");
        dimention.setGroupId("Dove0002");
        dimention.setMrp(160f);
        dimention.setDisplayPrice(new BigDecimal(20));
        dimention.setDiscount(10);
        dimention.setUnit("ML");
        dimention.setQty(500);
        dimention
                .setDescription("DESC that moisturizes and hydrates Number one dermatologist recommend");
        dimention.setOffer(true);
        dimention.setOfferDesc("Buy 1 get 1");
        dimention.setUnitsOnOrder(1);
        dimention.setUnitsInStock(250);
        dimention.setMfdDate(new Date());
        dimention.setExpiryDate(new Date());

        dimentions.add(dimention);

        item.setDimentions(dimentions);

        items.add(item);

        item = new Item();
        item.setDelearId("DL01");
        item.setUpc("12345678999");
        item.setCategoryId("200");
        item.setName("Riscue");
        item.setType("Shampoo");
        item.setBrand("Dove");
        item.setUsedFor("Anti Hair Fall");
        item.setIdealFor("Women");
        item.setComposition("1/4th Moisturizing Cream, Sodium Cocoyl Isethionate, Stearic Acid, Sodium Palmitate, Water, Lauric Acid, Perfume, Glycerin");
        item.setRanking("45");
        item.setAvgRating(new BigDecimal(4.5));

//        dimentions.clear();
        dimentions = new ArrayList<Dimention>();
        dimention = new Dimention();
        dimention.setSkuId("SK10");
        dimention.setGroupId("Dove0002");
        dimention.setMrp(160f);
        dimention.setDisplayPrice(new BigDecimal(20));
        dimention.setDiscount(10);
        dimention.setUnit("ML");
        dimention.setQty(10);
        dimention
                .setDescription("DESC that moisturizes and hydrates Number one dermatologist recommend");
        dimention.setOffer(true);
        dimention.setOfferDesc("Buy 1 get 1");
        dimention.setUnitsOnOrder(1);
        dimention.setUnitsInStock(250);
        dimention.setMfdDate(new Date());
        dimention.setExpiryDate(new Date());

        dimentions.add(dimention);

        dimention = new Dimention();
        dimention.setSkuId("SK11");
        dimention.setGroupId("Dove0002");
        dimention.setMrp(160f);
        dimention.setDisplayPrice(new BigDecimal(20));
        dimention.setDiscount(10);
        dimention.setUnit("ML");
        dimention.setQty(20);
        dimention
                .setDescription("DESC that moisturizes and hydrates Number one dermatologist recommend");
        dimention.setOffer(true);
        dimention.setOfferDesc("Buy 1 get 1");
        dimention.setUnitsOnOrder(1);
        dimention.setUnitsInStock(250);
        dimention.setMfdDate(new Date());
        dimention.setExpiryDate(new Date());

        dimentions.add(dimention);

        item.setDimentions(dimentions);

        items.add(item);

        response.items = items;
        return response;

    }
}
