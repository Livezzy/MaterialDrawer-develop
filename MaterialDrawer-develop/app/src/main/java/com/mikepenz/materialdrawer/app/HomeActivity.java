package com.mikepenz.materialdrawer.app;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.app.adapter.SubCategoryListingAdapter;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.core.UserManager;
import com.mikepenz.materialdrawer.app.handler.ISelection;
import com.mikepenz.materialdrawer.app.ui.SubCategoryListingFragment;
import com.mikepenz.materialdrawer.app.ui.TabFragment;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {
    private static final int PROFILE_SETTING = 1;
    private static final String TAG = "HomeActivity";
    //save our header or buildMainDrawer
    private AccountHeader headerResult = null;
    private Drawer mainDrawer = null;

    private Drawer subCategoryDrawer = null;


    private IProfile mainDrawerProfileItem;
    private Toolbar toolbar;

    private ProfileDrawerItem subCategoryDrawerProfileItem;
    private AccountHeader subCatHeaderResult;

    private ViewPager viewPager;
    private ActionBar actionBar;
    private Typeface menuItemTypeFace;
    private int menuItemColor;
    private SubCategoryListingFragment _subCategoryListingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

        menuItemTypeFace = Typeface.createFromAsset(getAssets(), "fonts/" + Constants.defaultNormalFont);
        Log.i(TAG, "buildMainDrawer()......default fonttypeface: " + menuItemTypeFace);
        menuItemColor = getResources().getColor(R.color.drawerTextColor);

        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setActonBarTitleBar(getString(R.string.app_name));

        buildSubCategoryDrawer();
        buildMainDrawer();

        subCategoryDrawer.closeDrawer();
        mainDrawer.closeDrawer();

        prepareMenuListing();

        setUpTabs();

        showDummyWaitDialog();
    }

    private void setUpTabs() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        final TabFragment tabFragment = new TabFragment();
        final ArrayList<String> tabTitles = new ArrayList<>();
        tabTitles.add("ALL");
        tabFragment.setHandler(new TabFragment.TabFragmentHandler() {
            @Override
            public void setAdapter(ViewPager viewPager) {
                viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), tabTitles));

            }
        });
        android.support.v4.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, tabFragment).commit();
    }

    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the header of the drawer with a compact/normal header
     *
     * @param compact
     */
    private void buildMainHeader(boolean compact) {

        mainDrawerProfileItem = new ProfileDrawerItem().withName("Hello, Masta Wayne!!").
                withEmail("Gurgaon, Hariyana").withIcon(GoogleMaterial.Icon.gmd_account);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        mainDrawerProfileItem
                )
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        mainDrawer.closeDrawer();
                        subCategoryDrawer.closeDrawer();
                        return true;
                    }
                })

                .build();
    }

    private void buildSubCategoryHeader() {

        subCategoryDrawerProfileItem = new ProfileDrawerItem().withIcon(GoogleMaterial.Icon.gmd_nature_people);

        subCatHeaderResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        subCategoryDrawerProfileItem
                )
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        subCategoryDrawer.closeDrawer();
                        mainDrawer.openDrawer();

                        return false;
                    }
                }).withProfileImagesClickable(true)

                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        updateMenuIconButton(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (mainDrawer != null && mainDrawer.isDrawerOpen()) {
            mainDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    private void buildMainDrawer() {
        buildMainHeader(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup headerView = (ViewGroup) inflater.inflate(R.layout.headerview, null);

        Resources resources = getResources();

        boolean isUserLoggedIn = UserManager.getInstance().isUserLoggedIn(this);
        String loginLogoutText = isUserLoggedIn ? "Logout" : "Login";

        mainDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.home)).withSelectable(false)
                                .withTextColor(menuItemColor),

                        new SecondaryDrawerItem().withName("Grocery & Staples").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.grocery)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Fruits & Vegetables").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.fruits)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Breakfast & Dairy").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.breakfast)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Beverages").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.beverages)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Branded Food").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.branded_food)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Personal Care").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.personal_care)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("World Store").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.world_store)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Household").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.household)).withTextColor(menuItemColor).withSelectable(false),

                        new SecondaryDrawerItem().withName("Cart").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.cart)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Wallet").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.wallet)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("My Account").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.account)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("FAQs").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.faq)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("About Livezzy").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.about_us)).withTextColor(menuItemColor).withSelectable(false),

                        new SecondaryDrawerItem().withName("Rate Us").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.rate_us)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Call Us").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.call)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName("Share").withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.share)).withTextColor(menuItemColor).withSelectable(false),
                        new SecondaryDrawerItem().withName(loginLogoutText).withTypeface(menuItemTypeFace).withIcon(resources.getDrawable(R.drawable.logout)).withTextColor(menuItemColor).withSelectable(false)

                )

                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        HomeActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
//                .addStickyDrawerItems(
//                        new SecondaryDrawerItem().withName("Shopping List").withIcon(GoogleMaterial.Icon.gmd_format_list_bulleted).withIdentifier(10)
//                                .withTextColor(getResources().getColor(R.color.drawerTextColor)),
//                        new SecondaryDrawerItem().withName("Magic Basket").withIcon(GoogleMaterial.Icon.gmd_shopping_basket).
//                                withTextColor(getResources().getColor(R.color.drawerTextColor))
//                )
//                .withStickyFooterDivider(true).withStickyFooter(R.layout.headerview)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        Toast.makeText(HomeActivity.this, "Hello" + drawerItem.getType(), Toast.LENGTH_SHORT).show();

                        switch (position) {
                            case 1:
                                break;
                            case 2:
                                updateSubCategory(groceryStamples);
                                break;
                            case 3:
                                updateSubCategory(fruitsAndVeg);
                                break;
                            case 4:
                                updateSubCategory(breakFast);
                                break;
                            case 5:
                                updateSubCategory(beverages);
                                break;
                            case 6:
                                updateSubCategory(brandedFoods);
                                break;
                            case 7:
                                updateSubCategory(personalCare);
                                break;
                            case 8:
                                updateSubCategory(worldStore);
                                break;
                            case 9:
                                updateSubCategory(houseHold);
                                break;
                            case 10:
                                navigateToCartScreen();
                                break;
                            case 11:
                                startActivity(new Intent(HomeActivity.this,WalletActivity.class));
                                break;
                            case 12:
                                startActivity(new Intent(HomeActivity.this, MyAccountActivity.class));
                                break;
                            case 13:
                                break;
                            case 14:
                                break;
                            case 15:
                                showRateUsDialog();
                                break;
                            case 16:
                                showContactDialogUsBox();
                                break;
                            case 17:
                                break;
                            case 18:
                                if (UserManager.getInstance().isUserLoggedIn(HomeActivity.this)) {
                                    UserManager.getInstance().logoutUser(HomeActivity.this);
                                    mainDrawer.closeDrawer();
                                } else {
                                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                    finish();
                                }
                                break;

                            default:
                                Log.i(TAG, "closing main and subcategory drawer.........");
                                mainDrawer.closeDrawer();
                                subCategoryDrawer.closeDrawer();
                                break;
                        }

                        return true;
                    }
                })
                .build();
    }

    private void navigateToCartScreen() {
        startActivity(new Intent(HomeActivity.this, CartActivity.class));
    }

    private void buildSubCategoryDrawer() {
        buildSubCategoryHeader();
        subCategoryDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar).withAccountHeader(subCatHeaderResult)
                .addDrawerItems(

                        new PrimaryDrawerItem().withTypeface(menuItemTypeFace).withIcon(GoogleMaterial.Icon.gmd_arrow_back)
                                .withTextColor(menuItemColor)

                ) // add the tabTitles we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
//                        Toast.makeText(HomeActivity.this, "HI.." + clickedView.getTag(), Toast.LENGTH_SHORT).show();

                        HomeActivity.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Toast.makeText(HomeActivity.this, "HI" + drawerItem.getType(), Toast.LENGTH_SHORT).show();
                        if (position == 1) {
                            subCategoryDrawer.closeDrawer();
                            mainDrawer.openDrawer();
                        } else {
                            Log.i(TAG, "sub cat drawer clicked: drawerItem.getTag()" + drawerItem.getTag());
                            subCategoryDrawer.closeDrawer();
                            mainDrawer.closeDrawer();
                            _subCategoryListingFragment.subCategoryListingRequest(drawerItem.getTag().toString());
                        }

                        return false;
                    }
                })
                .build();
    }

    private void updateSubCategory(ArrayList<String> list) {
        subCategoryDrawer.removeAllItems();

        buildSubCategoryHeader();
        subCategoryDrawer.addItem(new PrimaryDrawerItem().withIcon(GoogleMaterial.Icon.gmd_arrow_back));
        for (String item : list) {
            subCategoryDrawer.addItem(new SecondaryDrawerItem().withTag(item).withName(item).withTypeface(menuItemTypeFace).withIconColor(menuItemColor));
        }

        mainDrawer.closeDrawer();
        subCategoryDrawer.openDrawer();
    }

    private ArrayList<String> groceryStamples = new ArrayList<>();
    private ArrayList<String> fruitsAndVeg = new ArrayList<>();
    private ArrayList<String> breakFast = new ArrayList<>();
    private ArrayList<String> beverages = new ArrayList<>();
    private ArrayList<String> brandedFoods = new ArrayList<>();
    private ArrayList<String> personalCare = new ArrayList<>();
    private ArrayList<String> worldStore = new ArrayList<>();
    private ArrayList<String> houseHold = new ArrayList<>();


    private void prepareMenuListing() {
        groceryStamples.add("All Grocery & Stamples");
        groceryStamples.add("Dals & Pulses");
        groceryStamples.add("Dry Fruits");
        groceryStamples.add("Edible Oils");
        groceryStamples.add("Flours & Grains");
        groceryStamples.add("Spices");
        groceryStamples.add("Organic Staples");
        groceryStamples.add("Salst & Sugar");

        fruitsAndVeg.add("All Fruits & Vegetables");
        fruitsAndVeg.add("Cut Fruits & Vegetables");
        fruitsAndVeg.add("Organic Food & Vegetables");
        fruitsAndVeg.add("Fruits");
        fruitsAndVeg.add("Combos");
        fruitsAndVeg.add("Vegetables");

        breakFast.add("All Breakfast & Dairy");
        breakFast.add("Bread & Bakery");
        breakFast.add("Dairy Products");
        breakFast.add("Egg");


        beverages.add("All Beverages");
        beverages.add("Energy & Health Drinks");
        beverages.add("Fruit Drinks & Juices");
        beverages.add("Mineral Water");
        beverages.add("Soft Drinks");
        beverages.add("Tea & Coffee");

        brandedFoods.add("All Branded Food");
        brandedFoods.add("Baby Food");
        brandedFoods.add("Baking & Dessert Items");
        brandedFoods.add("Biscuits & Snacks");
        brandedFoods.add("Breakfast Cereals");
        brandedFoods.add("Canned Food");
        brandedFoods.add("Chocolates & Sweets");
        brandedFoods.add("Health  & Nutrition");
        brandedFoods.add("Indian Sweets");
        brandedFoods.add("Jams & Spreads");

        personalCare.add("All Personal Care");
        personalCare.add("Bath & Shower");
        personalCare.add("Baby Care");
        personalCare.add("Cosmetics");
        personalCare.add("Deos & Perfumes");
        personalCare.add("Fashion Accessories");
        personalCare.add("Grooming & Accessories");
        personalCare.add("Hair Care");

        personalCare.add("Home Linen");
        personalCare.add("Oral Care");
        personalCare.add("OTC");


        worldStore.add("All World Store");
        worldStore.add("Baby Food & Care");
        worldStore.add("Baking Needs");
        worldStore.add("Snacks");
        worldStore.add("Choclolates & Confectionaries");
        worldStore.add("Cooking Ingredients");
        worldStore.add("Dairy Imported");
        worldStore.add("Beverages");
        worldStore.add("Dry Fruits, Berries & Nuts");
        worldStore.add("Bakery & Pâtisserie");
        worldStore.add("Gourment Breakfast & Cereals");
        worldStore.add("Imported Non Food");
        worldStore.add("Jams, Honey & Spreads");
        worldStore.add("Oil & Vinegar");
        worldStore.add("Pasta, Rice & Noodles");
        worldStore.add("Ready to Cook Meals");
        worldStore.add("Sauces & Dips");
        worldStore.add("Soups & Instant Foods");
        worldStore.add("Tinned & Jarred Foods");

        houseHold.add("All Household");
        houseHold.add("Car Care");
        houseHold.add("Cleaning Accessories");
        houseHold.add("Consumer Durables");
        houseHold.add("Cookware");
        houseHold.add("Detergents");
        houseHold.add("Electricals");
        houseHold.add("Festive Decorative");
        houseHold.add("Mobile Accessories");
        houseHold.add("Gardening Needs");
        houseHold.add("Kitchen & Dining");
        houseHold.add("Kitchen Appliances");
        houseHold.add("Office & Stationery");
        houseHold.add("Pet Care");
        houseHold.add("Plastic Ware");
        houseHold.add("Pooja Needs");
        houseHold.add("Freshners & Repellents");
        houseHold.add("Safety Accessories");
        houseHold.add("Shoe Care");
        houseHold.add("Toilet & Floor Cleaners");
        houseHold.add("Utensils Cleaners");
        houseHold.add("Utilities");

    }


    class TabAdapter extends FragmentPagerAdapter {

        private ArrayList<String> tabTitles = new ArrayList<>();

        public TabAdapter(FragmentManager fm, ArrayList<String> tabTitles) {
            super(fm);
            this.tabTitles.clear();
            if (tabTitles != null) {
                for (String str : tabTitles) {
                    this.tabTitles.add(str);
                }
            }
        }

        /**
         * Return _subCategoryListingFragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    _subCategoryListingFragment = new SubCategoryListingFragment();
                    _subCategoryListingFragment.setActionHandler(new ISelection<SubCategoryListingAdapter.ListingItem>() {
                        @Override
                        public void selected(SubCategoryListingAdapter.ListingItem object) {
                            startActivity(new Intent(HomeActivity.this, ItemDetailsActivity.class));
                        }
                    });
                    _subCategoryListingFragment.setIActionHandler(new SubCategoryListingFragment.IActionHandler() {
                        @Override
                        public void startCartActivity() {
                            navigateToCartScreen();
                        }
                    });
                    return _subCategoryListingFragment;
            }
            return null;
        }

        @Override
        public int getCount() {

            return tabTitles.size();

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    SpannableString s = new SpannableString(tabTitles.get(position));
                    s.setSpan(menuItemTypeFace, 0, s.length(),
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                    return s;
                    return tabTitles.get(position);
                case 1:
                    SpannableString s1 = new SpannableString(tabTitles.get(position));
                    s1.setSpan(menuItemTypeFace, 0, s1.length(),
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                    return s1;
                    return tabTitles.get(position);

            }
            return null;
        }

    }

//    private void showContactUsDialog() {
//// custom dialog
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.custom);
//        dialog.setTitle("Title...");
//
//        // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText("Android custom dialog example!");
//        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//        image.setImageResource(R.drawable.ic_launcher);
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//        // if button is clicked, close the custom dialog
//        dialogButton.setOnClickListener(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
void showRateDialogBox() {

    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.getWindow().setBackgroundDrawable(
            new ColorDrawable(android.graphics.Color.TRANSPARENT));
    dialog.setContentView(R.layout.ratedialog);

    TextView sureButton = (TextView) dialog.findViewById(R.id.sureTExtview);
    TextView cancleButton = (TextView) dialog
            .findViewById(R.id.cancleRate_view);

    dialog.show();

    sureButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "U rate 5star me",
                    Toast.LENGTH_SHORT).show();

        }
    });
    cancleButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           dialog.dismiss();
        }
    });

}

    void showContactDialogUsBox() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.contact_dialog);

        TextView callButton = (TextView) dialog
                .findViewById(R.id.callTExtview);
        TextView cancleButton = (TextView) dialog
                .findViewById(R.id.cancleContact_view);

        dialog.show();

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This is the call",
                        Toast.LENGTH_SHORT).show();

            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //
                dialog.dismiss();
            }
        });

    }

    void showFeedbackDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.feedback_dialog);

        TextView callButton = (TextView) dialog
                .findViewById(R.id.surefeedTExtview);
        TextView cancleButton = (TextView) dialog
                .findViewById(R.id.cancleFeedback_view);

        dialog.show();

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This is the sure",
                        Toast.LENGTH_SHORT).show();

            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //
               dialog.dismiss();
            }
        });

    }

    void showRateUsDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.rate_us);
        ImageView sadButton = (ImageView) dialog.findViewById(R.id.sadRateImageView);
        ImageView happyButton = (ImageView) dialog.findViewById(R.id.happyRateImageView);

        Log.i("sadButton", "aaaaaaaaaaaaaaa"+sadButton+happyButton+dialog);
        dialog.show();
        sadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showFeedbackDialog();
            }
        });
        happyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showRateDialogBox();
            }
        });
    }
}
