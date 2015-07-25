package vienan.app.journey.util;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BDLocationClient {

	private Context mContext;
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private OnLocationListener mLocationListener = null;

	public BDLocationClient(Context context) {
		mContext = context;
		initLocation();
	}

	private void initLocation() {
		mLocationClient = new LocationClient(mContext); // 声明LocationClient类
		// mLocationClient.setAK("8mrnaFzKu3DoduLnWuB5Lt2w"); //V4.0
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		setLocationOption();
		mLocationClient.start();// 开始定位
		Log.d(GlobalConstants.TAG, "[BDLocationClient]->initLocation");
	}

	public void stopLocation() {
		mLocationClient.stop();// 停止定位
	}

	public void requestLocation() {
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		else
			Log.d(GlobalConstants.TAG, "locClient is null or not started");
	}

	/*public void requestPoi() {
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestPoi();
	}*/

	/**
	 * 设置相关参数
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("当前时间 : ");
			sb.append(location.getTime());
			sb.append("\n错误码 : ");
			sb.append(location.getLocType());
			sb.append("\n纬度 : ");
			sb.append(location.getLatitude());
			sb.append("\n经度 : ");
			sb.append(location.getLongitude());
			sb.append("\n定位精度 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\n速度 : ");
				sb.append(location.getSpeed());
				sb.append("\n卫星数 : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n省份 : ");
				sb.append(location.getProvince());
				sb.append("\n城市 : ");
				sb.append(location.getCity());
				sb.append("\n区/县 : ");
				sb.append(location.getDistrict());
				sb.append("\n街道: ");
				sb.append(location.getStreet());
				sb.append("\n街道号码: ");
				sb.append(location.getStreetNumber());
				sb.append("\n地址 : ");
				sb.append(location.getAddrStr());
				mLocationListener.OnUpdateLocation(location.getCity(),
						location.getAddrStr());
			}
			//Log.d(GlobalConstants.TAG, "onReceiveLocation " + sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// 将在下个版本中去除poi功能
		}
	}

	/**
	 * 设置城市和详细地址
	 *
	 * @param listener
	 */
	public void setOnLocationListener(OnLocationListener listener) {
		mLocationListener = listener;
	}

	public interface OnLocationListener {
		public void OnUpdateLocation(String city, String addr);
	}
}

// 百度基站定位错误返回码
// 61 ： GPS定位结果
// 62 ： 扫描整合定位依据失败。此时定位结果无效。
// 63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
// 65 ： 定位缓存的结果。
// 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
// 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
// 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
// 161： 表示网络定位结果
// 162~167： 服务端定位失败
// 502：KEY参数错误
// 505：KEY不存在或者非法
// 601：KEY服务被开发者自己禁用
// 602: KEY Mcode不匹配,意思就是您的ak配置过程中安全码设置有问题，请确保： sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名
// 501-700：KEY验证失败