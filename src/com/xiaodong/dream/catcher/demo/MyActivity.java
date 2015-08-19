package com.xiaodong.dream.catcher.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
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

    private Toolbar mToolbar;

    private static final int PROFILE_SETTING = 1;

    private Fragment mCurrentFragment = null;

    private Drawer result;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //init client manager
        ClientManager.init(this.getApplicationContext());

        initToolbar(savedInstanceState);

        initView();
    }

    private void initToolbar(Bundle savedInstanceState) {
        // Handle Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Xiaodong").withEmail("xiaodong@conversant.com.cn").withIcon(getResources().getDrawable(R.drawable.profile3));

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_swiftsync).withIcon(R.drawable.logo_swiftsync).withIdentifier(1).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_storeshare).withIcon(R.drawable.logo_storeshare).withIdentifier(2).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_swiftmedia).withIcon(R.drawable.logo_swiftmedia).withIdentifier(3).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_viewstream).withIcon(R.drawable.logo_viewstream).withIdentifier(4).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_escape).withIcon(R.drawable.logo_escape).withIdentifier(5).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_swiftsms).withIcon(R.drawable.logo_swiftsms).withIdentifier(6).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_dreamcatcher).withIcon(R.drawable.logo).withIdentifier(7).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(8).withCheckable(false)
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

    private void initView(){
        String fragmentClassName = MainConfiguration.getFragmentClassName(1);

        if (fragmentClassName == null){
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        try {
            mCurrentFragment = (Fragment) Class.forName(fragmentClassName).newInstance();

            int identifier = -1;

            if (result != null) {
                identifier = result.getDrawerItems().get(result.getCurrentSelection()).getIdentifier();
            }
            fragmentTransaction.add(R.id.main_container, mCurrentFragment, String.valueOf(identifier));
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
        if (mToolbar != null) {
            mToolbar.setTitle(resourceId);
        }
    }

    @Override
    public void onSetMainTitle(String string) {

    }
}
