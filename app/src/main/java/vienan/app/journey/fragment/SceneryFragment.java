package vienan.app.journey.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vienan.app.journey.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by lenovo on 2015/7/25.
 */
public class SceneryFragment extends Fragment implements ScreenShotable{

    @Bind(R.id.iv_scenery)
    ImageView ivScenery;
    @Bind(R.id.btn_takePhoto)
    Button btnTakePhoto;
    private static final int TAKE_PHOTO=1;
    private static final int CROP_PHOTO=2;
    private Uri imageUri;
    View containerView;
    Bitmap bitmap;
    File file;
    public static final String path= Environment.getExternalStorageDirectory().getPath()+"/scenery/";
    public static final String imgName=DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Scenery");
        View rootView = inflater.inflate(R.layout.fragment_scenery, container, false);
        ButterKnife.bind(this, rootView);
        file=new File(path);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containerView=view.findViewById(R.id.scenery_container);
    }

    @OnClick(R.id.btn_takePhoto)
    void takePhoto(){
        if(!file.exists()) {
            Log.i("file","file makeDir");
            file.mkdirs();// 创建文件夹
        }
        String imgName= DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        File imgFile=new File(file.getPath(),imgName);
        try {
            if (imgFile.exists()) {
                imgFile.delete();
            }
            imgFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(imgFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle("Journey");
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==getActivity().RESULT_OK){
                        Log.i("test","execute crop");
                        cropImageUri(imageUri, 800, 400, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    if (imageUri != null) {
                        Bitmap bitmap = decodeUriAsBitmap(imageUri);
                        ivScenery.setImageBitmap(bitmap);
                    }else {

                        Log.e("image","is not have");
                    }
                }
                break;
            default:
                break;
        }
    }
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 2);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", outputX);

        intent.putExtra("outputY", outputY);

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, requestCode);

    }
    private Bitmap decodeUriAsBitmap(Uri uri){

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {

            e.printStackTrace();

            return null;

        }

        return bitmap;

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
                    SceneryFragment.this.bitmap = bitmap;
                }
            };

            thread.start();
        }

        @Override
        public Bitmap getBitmap() {
            return bitmap;
        }
}
