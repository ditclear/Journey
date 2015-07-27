package vienan.app.journey.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import vienan.app.journey.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by lenovo on 2015/7/26.
 */
public class GalleryFragment extends Fragment implements ScreenShotable {
    View containerView;
    Bitmap bitmap;
    @Bind(R.id.first_column)
    LinearLayout firstColumn;
    @Bind(R.id.second_column)
    LinearLayout secondColumn;
    @Bind(R.id.third_column)
    LinearLayout thirdColumn;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle("Gallery");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containerView = view.findViewById(R.id.my_scroll_view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                GalleryFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
