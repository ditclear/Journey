package vienan.app.journey.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.avos.avoscloud.AVOSCloud;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.widget.RevealFrameLayout;
import vienan.app.journey.R;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {

    @Bind(R.id.content_overlay)
    LinearLayout contentOverlay;
    @Bind(R.id.content_frame)
    LinearLayout contentFrame;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container_frame)
    RevealFrameLayout containerFrame;
    @Bind(R.id.left_drawer)
    LinearLayout leftDrawer;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ViewAnimator viewAnimator;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(this, "3u37r7aa44uzd42uc6k9wlcacuiu489gcpv52552ik55p7m0", "9fozcj0qxqjezprcxf3d7zu1fjgre288bnw5qt8w0lgwg6oe");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble resourceble, ScreenShotable screenShotable, int i) {
        return null;
    }

    @Override
    public void disableHomeButton() {

    }

    @Override
    public void enableHomeButton() {

    }

    @Override
    public void addViewToContainer(View view) {

    }
}
