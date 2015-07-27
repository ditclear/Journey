package vienan.app.journey.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import vienan.app.journey.R;
import vienan.app.journey.fragment.CompassFragment;
import vienan.app.journey.fragment.ContentFragment;
import vienan.app.journey.fragment.GalleryFragment;
import vienan.app.journey.fragment.MapFragment;
import vienan.app.journey.fragment.SceneryFragment;
import vienan.app.journey.fragment.WeatherFragment;
import vienan.app.journey.util.SlideInAnimationHandler;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;
public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {

    @Bind(R.id.left_drawer)
    LinearLayout linearLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_frame)
    LinearLayout view;
    @Bind(R.id.content_overlay)
    LinearLayout contentOverlay;

    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private int res = R.drawable.content_music;
    public static boolean isMap=false;
    MapFragment mapFragment=new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        contentFragment = ContentFragment.newInstance(R.drawable.content_music);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<SlideMenuItem>(this, list, contentFragment, drawerLayout, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Journey");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addMenuBtn() {
        ImageView fabContent = new ImageView(this);
        fabContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_settings));

        FloatingActionButton darkButton = new FloatingActionButton.Builder(this)
                .setTheme(FloatingActionButton.THEME_DARK)
                .setContentView(fabContent)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this)
                .setTheme(SubActionButton.THEME_DARK);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);
        ImageView rlIcon5 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place));
        rlIcon5.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_headphones));

        // Set 4 SubActionButtons
        FloatingActionMenu centerBottomMenu= new FloatingActionMenu.Builder(this)
                .setStartAngle(0)
                .setEndAngle(-180)
                .setAnimationHandler(new SlideInAnimationHandler())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon5).build())
                .attachTo(darkButton)
                .build();

    }
    @OnClick(R.id.left_drawer)
    void left_drawer() {
        Log.d("tag", "execute left_drawer");
        drawerLayout.closeDrawers();
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.PARTY, R.drawable.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.MOVIE, R.drawable.icn_7);
        list.add(menuItem7);
    }


    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        contentOverlay.setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;
            case ContentFragment.BOOK:
                startAnimator(position);
                CompassFragment compassFragment=new CompassFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,compassFragment).commit();
                return compassFragment;
            case ContentFragment.BUILDING:
               // startActivity(new Intent(this, MapActivity.class));
                //isMap=true;
                startAnimator(position);
                MapFragment mapFragment=new MapFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,mapFragment).commit();
                /*if (isMap){
                    addMenuBtn();
                }*/
                return mapFragment;
            case ContentFragment.PAINT:
                startAnimator(position);
                WeatherFragment weatherFragment=new WeatherFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,weatherFragment).commit();
                return weatherFragment;
            case ContentFragment.CASE:
                startAnimator(position);
                SceneryFragment sceneryFragment=new SceneryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,sceneryFragment).commit();
                return sceneryFragment;
            case ContentFragment.SHOP:
                startAnimator(position);
                GalleryFragment galleryFragment=new GalleryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,galleryFragment).commit();
                return galleryFragment;
            default:
                isMap=false;
        }
        return replaceFragment(screenShotable, position);
    }

    public void startAnimator(int position) {
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        animator.start();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    *//**
     * 默认点击menu菜单，菜单项不现实图标，反射强制其显示
     *//*
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {

        if (featureId == Window.FEATURE_OPTIONS_PANEL && menu != null)
        {
            if (menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e)
                {
                }
            }

        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId())
        {
            case R.id.id_menu_map_common:
                // 普通地图
                mapFragment.getmBaiduMap().setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.id_menu_map_site:// 卫星地图
                mapFragment.getmBaiduMap().setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.id_menu_map_traffic:
                // 开启交通图

                if ( mapFragment.getmBaiduMap().isTrafficEnabled())
                {
                    item.setTitle("开启实时交通");
                    mapFragment.getmBaiduMap().setTrafficEnabled(false);
                } else
                {
                    item.setTitle("关闭实时交通");
                    mapFragment.getmBaiduMap().setTrafficEnabled(true);
                }
                break;
            case R.id.id_menu_map_myLoc:
                mapFragment.center2myLoc();
                break;
            case R.id.id_menu_map_style:
                mapFragment.mCurrentStyle = (++ mapFragment.mCurrentStyle) %  mapFragment.mStyles.length;
                item.setTitle( mapFragment.mStyles[ mapFragment.mCurrentStyle]);
                // 设置自定义图标
                switch ( mapFragment.mCurrentStyle)
                {
                    case 0:
                        mapFragment.mCurrentMode = LocationMode.NORMAL;
                        break;
                    case 1:
                        mapFragment.mCurrentMode = LocationMode.FOLLOWING;
                        break;
                    case 2:
                        mapFragment.mCurrentMode = LocationMode.COMPASS;
                        break;
                }
                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                        .fromResource(R.drawable.navi_map_gps_locked);
                MyLocationConfigeration config = new MyLocationConfigeration(
                        mapFragment. mCurrentMode, true, mCurrentMarker);
                mapFragment.getmBaiduMap().setMyLocationConfigeration(config);
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
