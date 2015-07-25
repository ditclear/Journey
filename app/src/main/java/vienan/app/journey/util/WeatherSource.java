package vienan.app.journey.util;

import android.os.AsyncTask;

public class WeatherSource {

	private boolean mIsFinish = false;
	private String mResult;
	private OnFinishListener mFinishListener = null;
	private String mCity;

	public WeatherSource(String city) {
		mCity = city;
		new GetSource().execute();
	}

	public boolean isFinish() {
		return mIsFinish;
	}

	public String getResult() {
		return mResult;
	}

	class GetSource extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			GlobalConstants.PrintLog_D("[GetSource->doInBackground]");
			mIsFinish = false;
			try { // http://www.weather.com.cn/data/sk/101280601.html 不再更新天气
				String path = "http://api.map.baidu.com/telematics/v3/weather?location="
						+ mCity
						+ "&output=xml&ak=640f3985a6437dad8135dae98d775a09";
				return NetworkUtils.HttpGet(path);
			} catch (Exception e) {
				System.out.println("doInBackground--Exception");
				e.printStackTrace();
				return null;
			} // http://61.4.185.48:81/g/
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mResult = result;
			mIsFinish = true;
			if (mFinishListener != null) {
				mFinishListener.OnFinish(result);
			}
			GlobalConstants.PrintLog_D("[GetSource->onPostExecute]" + result);
		}
	}

	public void setOnFinishListener(OnFinishListener listener) {
		mFinishListener = listener;
	}

	public interface OnFinishListener {
		public void OnFinish(String result);
	}
}
