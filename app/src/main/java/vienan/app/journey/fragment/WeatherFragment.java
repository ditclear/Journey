package vienan.app.journey.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParserFactory;

import vienan.app.journey.R;
import vienan.app.journey.utils.BDLocationClient;
import vienan.app.journey.utils.DrawPicNum;
import vienan.app.journey.utils.GlobalConstants;
import vienan.app.journey.utils.WeatherHandler;
import vienan.app.journey.utils.WeatherInfo;
import vienan.app.journey.utils.WeatherSource;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by lenovo on 2015/7/24.
 */
public class WeatherFragment extends Fragment implements ScreenShotable {
    Activity activity;
    private View weather_container;
    private  Bitmap bitmap;
    private TextView mText;
    //private ImageView mIvTemp;
    private TextView mWeather;
    private TextView mDate;
    private TextView mWeek;
    private TextView mTemp;
    private ImageView mIcon;
    private Bitmap mBitmapIcon;
    private BDLocationClient mLocationClient;
    private WeatherSource mWeatherSource;
    private WeatherHandler weatherHandler = null;
    private ArrayList<WeatherInfo> weatherInfos;
    private DrawPicNum picNum;
    private AsyncDownload mDownload = null;
    private boolean _isExe = false;

    public WeatherFragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weather_container=view.findViewById(R.id.weather_container);
    }
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        activity.setTitle("Journey");
        if (_isExe) {
            mDownload.cancel(true); // 取消操作
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (_isExe) {
            mDownload.cancel(true); // 取消操作
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (_isExe) {
            mDownload.cancel(true); // 取消操作
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_weather,container,false);
        mLocationClient = new BDLocationClient(getActivity().getApplicationContext());
        mLocationClient.setOnLocationListener(new LocationListener());
        initWidgets(rootView);
        return rootView;
    }
    private void initWidgets(View rootView) {
        mText = (TextView) rootView.findViewById(R.id.tv_text);
        //mIvTemp = (ImageView) rootView.findViewById(R.id.iv_temp);
        mWeather = (TextView) rootView.findViewById(R.id.tv_weather);
        mDate = (TextView) rootView.findViewById(R.id.tv_date);
        mWeek = (TextView) rootView.findViewById(R.id.tv_week);
        mTemp = (TextView) rootView.findViewById(R.id.tv_temp);
        mIcon = (ImageView) rootView.findViewById(R.id.iv_icon);
        Button btn = (Button) rootView.findViewById(R.id.btn_request);
        //btn.setOnClickListener(this);
        weatherInfos = new ArrayList<WeatherInfo>();
        picNum = new DrawPicNum(getActivity());
        mDownload = new AsyncDownload();
    }

    private class LocationListener implements BDLocationClient.OnLocationListener {

        @Override
        public void OnUpdateLocation(String city, String addr) {
            // TODO Auto-generated method stub
            Log.e("error",city+","+addr+activity+","+mText);
            activity.setTitle("当前城市：" + city);
            mText.setText(addr);
            // 定位后再获取当前城市的天气
            mWeatherSource = new WeatherSource(city);
            mWeatherSource.setOnFinishListener(new FinishListener());
        }

    }

    private class FinishListener implements WeatherSource.OnFinishListener {

        @Override
        public void OnFinish(String result) {
            // TODO Auto-generated method stub
            try {
                // 创建一个SAXParserFactory
                SAXParserFactory factory = SAXParserFactory.newInstance();
                XMLReader reader = factory.newSAXParser().getXMLReader();
                weatherHandler = new WeatherHandler();
                // 为XMLReader设置内容处理器
                reader.setContentHandler(weatherHandler);
                // 开始解析文件
                reader.parse(new InputSource(new StringReader(result)));
                parseWeather();
            } catch (Exception e) {
                System.out.println("-----------Exception");
                e.printStackTrace();
            }
        }
    }

    private void parseWeather() {
        weatherInfos = weatherHandler.getWeathers();
        WeatherInfo weather = new WeatherInfo();
        weather = weatherInfos.get(0);
        /*mIvTemp.setImageBitmap(picNum.getStringPic(getNumberInString(weather
                .getDate())));*/
        mWeather.setText(weather.getWeather() + "  " + weather.getWind());
        mDate.setText(weatherHandler.getDate());
        String date = weather.getDate();
        mWeek.setText(date.substring(date.lastIndexOf("实"),date.length()-1));
        Log.e("date", date);
        Log.i("weather", weather.getWeather());
        if(weather.getWeather().equals("阴")) {
            weather_container.setBackgroundResource(R.drawable.yin);
        }else if(weather.getWeather().equals("多云")) {
            weather_container.setBackgroundResource(R.drawable.dy);
        } else if(weather.getWeather().equals("晴")) {
            weather_container.setBackgroundResource(R.drawable.q);
        }else if(weather.getWeather().contains("小雪")) {
            weather_container.setBackgroundResource(R.drawable.xx);
        }else if(weather.getWeather().contains("雾")) {
            weather_container.setBackgroundResource(R.drawable.w);
        }else if(weather.getWeather().contains("小雨")) {
            weather_container.setBackgroundResource(R.drawable.xyu);
        }else if(weather.getWeather().contains("中雨")) {
            weather_container.setBackgroundResource(R.drawable.zy);
        }else if(weather.getWeather().contains("阵雨")) {
            weather_container.setBackgroundResource(R.drawable.zheny);
        }else if(weather.getWeather().contains("暴雨")) {
            weather_container.setBackgroundResource(R.drawable.by);
        }else if(weather.getWeather().contains("雷阵雨")) {
            weather_container.setBackgroundResource(R.drawable.lzy);
        }else if(weather.getWeather().contains("冰雹")) {
            weather_container.setBackgroundResource(R.drawable.bb);
        }else if(weather.getWeather().contains("沙尘暴")) {
            weather_container.setBackgroundResource(R.drawable.scb);
        } else {
            weather_container.setBackgroundResource(R.drawable.bg);
        }
        mTemp.setText(weather.getTemperature());
        if (!_isExe) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH",
                    Locale.getDefault());
            String hour = sdf.format(new Date());
            if (Integer.parseInt(hour) > 18 || Integer.parseInt(hour) < 6) {
                // 晚上
                mDownload.execute(weather.getNightPictureUrl()); // 执行异步操作
            } else {
                // 白天
                mDownload.execute(weather.getDayPictureUrl()); // 执行异步操作
            }
            System.out.println("hour = " + hour);
            _isExe = true;
        }
        weather = weatherInfos.get(1);
        TextView text = (TextView) getView().findViewById(R.id.tv_date1);
        text.setText(weather.getDate());
        text = (TextView)getView().findViewById(R.id.tv_temp1);
        text.setText(weather.getTemperature());
        text = (TextView) getView().findViewById(R.id.tv_weather1);
        text.setText(weather.getWeather());
        weather = weatherInfos.get(2);
        text = (TextView) getView().findViewById(R.id.tv_date2);
        text.setText(weather.getDate());
        text = (TextView) getView().findViewById(R.id.tv_temp2);
        text.setText(weather.getTemperature());
        text = (TextView) getView().findViewById(R.id.tv_weather2);
        text.setText(weather.getWeather());
        weather = weatherInfos.get(3);
        text = (TextView) getView().findViewById(R.id.tv_date3);
        text.setText(weather.getDate());
        text = (TextView) getView().findViewById(R.id.tv_temp3);
        text.setText(weather.getTemperature());
        text = (TextView) getView().findViewById(R.id.tv_weather3);
        text.setText(weather.getWeather());

    }

    /**
     * 从字符串中得到数字
     *
     * @param str
     * @return
     */
    private String getNumberInString(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return (m.replaceAll("").trim());
    }

    class AsyncDownload extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            URL imageUrl = null;
            try {
                imageUrl = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(GlobalConstants.TAG, e.getMessage());
            }
            try {
                // 使用HttpURLConnection打开连接
                HttpURLConnection urlConn = (HttpURLConnection) imageUrl
                        .openConnection();
                urlConn.setDoInput(true);
                urlConn.connect();
                // 将得到的数据转化成InputStream
                InputStream is = urlConn.getInputStream();
                // 将InputStream转换成Bitmap
                mBitmapIcon = BitmapFactory.decodeStream(is);
                is.close();
                System.out.println("-------------success");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(GlobalConstants.TAG, e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            mIcon.setImageBitmap(mBitmapIcon);
            System.out.println("-------------success--1");
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }
    }


    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(weather_container.getWidth(),
                        weather_container.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                weather_container.draw(canvas);
                WeatherFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
