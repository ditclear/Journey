package vienan.app.journey.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络相关工具类
 *
 * @author dzt 2014.04.10
 * @<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *                   权限
 */
public class NetworkUtils {

	/**
	 * 判断网络是否可用
	 *
	 * @param context
	 *            上下文对象
	 * @return true网络可用，否则不可用
	 */
	public static Boolean DetectNetwork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 通过Get获取网页内容
	 *
	 * @param url
	 *            地址只能传入.xml后缀
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String HttpGet(String url) throws ClientProtocolException,
			IOException {
		// 新建一个默认的连接
		DefaultHttpClient client = new DefaultHttpClient();
		// 新建一个Get方法
		HttpGet get = new HttpGet(url);
		// 得到网络的回应
		HttpResponse response = client.execute(get);
		// 获得的网页源代码（xml）
		String content = null;

		// 如果服务器响应的是OK的话！
		if (response.getStatusLine().getStatusCode() == 200) {
			// 以下是把网络数据分段读取下来的过程
			InputStream in = response.getEntity().getContent();
			byte[] data = new byte[1024];
			int length = 0;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			while ((length = in.read(data)) != -1) {
				bout.write(data, 0, length);
			}
			// 最后把字节流转为字符串 转换的编码为utf-8.
			content = new String(bout.toByteArray(), "utf-8");
		}
		// 返回得到的字符串 也就是网页源代码
		return content;
	}

	/**
	 * 得到网页的内容，以字符串形式
	 *
	 * @param path
	 *            传入html路径
	 * @return
	 * @throws Exception
	 */
	public static String getHtml(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);

		InputStream inStream = conn.getInputStream();// 通过输入流获取html数据
		byte[] data = readInputStream(inStream);// 得到html的二进制数据
		String html = new String(data, "utf-8");
		return html;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}

