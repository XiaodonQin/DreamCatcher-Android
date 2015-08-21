package com.xiaodong.dream.catcher.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.xiaodong.dream.catcher.demo.manager.ClientManager;


public class MyActivity extends AppCompatActivity implements MyFragment.OnSetMainTitleListener {
    private static String TAG = "MyActivity";

    private Toolbar mToolbar;

    private static final int PROFILE_SETTING = 1;

    private Fragment mCurrentFragment = null;

    private Drawer result;

    private AccountHeader headerResult;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, ">>onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //init client manager
        ClientManager.init(this.getApplicationContext());

        initToolbar(savedInstanceState);

        initContentView();
    }

    private void initToolbar(Bundle savedInstanceState) {
        Log.i(TAG, ">>initToolbar");

        // Handle Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Xiaodong").withEmail("xiaodong@conversant.com.cn").withIcon(getResources().getDrawable(R.drawable.profile3));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings),
                        new ProfileSettingDrawerItem().withName("Log Out").withIcon(GoogleMaterial.Icon.gmd_account_circle)
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_swiftsync).withIcon(R.drawable.logo_swiftsync).withIdentifier(2).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_storeshare).withIcon(R.drawable.logo_storeshare).withIdentifier(3).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_swiftmedia).withIcon(R.drawable.logo_swiftmedia).withIdentifier(4).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_viewstream).withIcon(R.drawable.logo_viewstream).withIdentifier(5).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_escape).withIcon(R.drawable.logo_escape).withIdentifier(6).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_swiftsms).withIcon(R.drawable.logo_swiftsms).withIdentifier(7).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_dreamcatcher).withIcon(R.drawable.logo).withIdentifier(8).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(9).withCheckable(false)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            changeFragment(drawerItem);
                            result.setSelectionByIdentifier(drawerItem.getIdentifier(), false);
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelectionByIdentifier(1, false);
            //set the active profile
            headerResult.setActiveProfile(profile);
        }
    }

    private void initContentView(){
        Log.i(TAG, ">>initContentView");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        try {
            int identifier = -1;
            String fragmentClassName = null;

            if (result != null) {
                identifier = result.getDrawerItems().get(result.getCurrentSelection()).getIdentifier();

                fragmentClassName = MainConfiguration.getFragmentClassName(identifier);

                if(fragmentClassName == null){
                    return;
                }

                mCurrentFragment = (Fragment) Class.forName(fragmentClassName).newInstance();
            }
            fragmentTransaction.replace(R.id.main_container, mCurrentFragment, String.valueOf(identifier));
            fragmentTransaction.commit();

            fragmentManager.executePendingTransactions();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void changeFragment(IDrawerItem drawerItem){
        Log.i(TAG, ">>changeFragment");

        if (drawerItem == null){
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(drawerItem.getIdentifier()));

        if(fragment != null && fragment == mCurrentFragment){
            fragmentTransaction.show(fragment);
        }else {
            try {
                String fragmentClassName = MainConfiguration.getFragmentClassName(drawerItem.getIdentifier());

                mCurrentFragment = (Fragment) Class.forName(fragmentClassName).newInstance();
                fragmentTransaction.replace(R.id.main_container, mCurrentFragment, String.valueOf(drawerItem.getIdentifier()));
                fragmentTransaction.commit();

                fragmentManager.executePendingTransactions();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSetMainTitle(int resourceId) {
        Log.i(TAG, ">>onSetMainTitle");

        if (mToolbar != null) {
            mToolbar.setTitle(resourceId);
        }
    }

    @Override
    public void onSetMainTitle(String string) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, ">>onSaveInstanceState");
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, ">>onBackPressed");

        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
