package vienan.app.journey.util;

import android.os.Environment;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 定义全局常量
 *
 * @author dzt 2014.04.01
 *
 */
public class GlobalConstants {
	public static final String TAG = "dzt";

	public static void PrintLog_D(String str) {
		Log.d(TAG, str);
	}

	/**
	 * 两次按返回键的间隔判断
	 */
	public static final int EXIT_TIME = 2000;

	/**
	 * 日期格式定义 2014.04.01
	 */
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	public static final SimpleDateFormat DEFAULT_DATE_NAME = new SimpleDateFormat(
			"yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
	public static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm", Locale.getDefault());
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.getDefault());
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"MM/dd", Locale.getDefault());
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm", Locale.getDefault());

	/**
	 * 得到SD卡绝对路径
	 */
	public static final String SD_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath();
}
