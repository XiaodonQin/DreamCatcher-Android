package com.xiaodong.dream.catcher.demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

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
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;
import com.mikepenz.octicons_typeface_library.Octicons;
import com.xiaodong.dream.catcher.demo.douban.DoubanFragment;
import com.xiaodong.dream.catcher.demo.express.ExpressFragment;
import com.xiaodong.dream.catcher.demo.manager.ClientManager;
import com.xiaodong.dream.catcher.demo.tencent.TencentFragment;
import com.xiaodong.dream.catcher.demo.weibo.WeiboFragment;
import com.xiaodong.dream.catcher.demo.weixin.WeixinFragment;

import com.mikepenz.octicons_typeface_library.Octicons;


public class MyActivity extends AppCompatActivity implements MyFragment.OnSetMainTitleListener{

    private FragmentTabHost fragmentTabHost;
    private LayoutInflater layoutInflater;

    private TextView mainTitle;
    private ImageView backBtn;
    private ImageView menuBtn;

    private Toolbar mToolbar;

    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    private Class fragmentArray[] = {
            WeixinFragment.class, WeiboFragment.class,
            TencentFragment.class, DoubanFragment.class,
            ExpressFragment.class
    };
    private int nameArray[] = {
            R.string.weixin_title, R.string.weibo_title,
            R.string.tencent_title, R.string.douban_title,
            R.string.express_title
    };
    private int iconArray[] = {
            R.drawable.logo_weixin, R.drawable.logo_sina,
            R.drawable.logo_tencent, R.drawable.logo_douban,
            R.drawable.logo_express
    };
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

    private void initToolbar(Bundle savedInstanceState){
        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
        final IProfile profile3 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile2));
        final IProfile profile4 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile3));
        final IProfile profile5 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile4)).withIdentifier(4);
        final IProfile profile6 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        profile6,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(GoogleMaterial.Icon.gmd_wb_sunny).withIdentifier(1).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4).withCheckable(false),
                        new PrimaryDrawerItem().withDescription("A more complex sample").withName(R.string.drawer_item_complex_header_drawer).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_simple_fragment_drawer).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(6).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_embedded_drawer_dualpane).withIcon(GoogleMaterial.Icon.gmd_battery_charging_full).withIdentifier(7).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_fullscreen_drawer).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(8).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom_container_drawer).withIcon(GoogleMaterial.Icon.gmd_my_location).withIdentifier(9).withCheckable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_with_menu).withIcon(GoogleMaterial.Icon.gmd_list).withIdentifier(10).withCheckable(false),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github).withIdentifier(20).withCheckable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(11).withTag("Bullhorn"),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName("Switch").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new SwitchDrawerItem().withName("Switch2").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener),
                        new ToggleDrawerItem().withName("Toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

//                        if (drawerItem != null) {
//                            Intent intent = null;
//                            if (drawerItem.getIdentifier() == 1) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleCompactHeaderDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 2) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, ActionBarDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 3) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, MultiDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 4) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleNonTranslucentDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 5) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, ComplexHeaderDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 6) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleFragmentDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 7) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, EmbeddedDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 8) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, FullscreenDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 9) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, CustomContainerActivity.class);
//                            }  else if (drawerItem.getIdentifier() == 10) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, MenuDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 20) {
//                                intent = new LibsBuilder()
//                                        .withFields(R.string.class.getFields())
//                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
//                                        .intent(SimpleHeaderDrawerActivity.this);
//                            }
//                            if (intent != null) {
//                                SimpleHeaderDrawerActivity.this.startActivity(intent);
//                            }
//                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelectionByIdentifier(11, false);

            //set the active profile
            headerResult.setActiveProfile(profile3);
        }
    }

    private void initView(){
        layoutInflater = LayoutInflater.from(this);

//        mainTitle = (TextView) findViewById(R.id.title);
//        backBtn = (ImageView) findViewById(R.id.back_btn);
//        menuBtn = (ImageView) findViewById(R.id.menu_btn);
//
//        menuBtn.setVisibility(View.VISIBLE);

        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.content_container_layout);
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        setupTabHost();

    }

    private void setupTabHost(){
        int count = fragmentArray.length;
        for(int i = 0; i < count; i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(getResources().getString(nameArray[i]))
                    .setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            fragmentTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_bg);
        }

        fragmentTabHost.setCurrentTab(0);
    }

    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.item_tab_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(iconArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tab_text);
        textView.setText(nameArray[index]);

        return view;
    }

    @Override
    public void onSetMainTitle(int resourceId) {
        if(mainTitle != null){
            mainTitle.setText(resourceId);
        }

        if (mToolbar != null){
            mToolbar.setTitle(resourceId);
        }
    }

    @Override
    public void onSetMainTitle(String string) {

    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };
}
