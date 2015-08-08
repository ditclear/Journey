package vienan.app.journey.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * 使用SAX解析百度车联网天气查询得到的xml文件。 http://developer.baidu.com/map/carapi-7.htm
 *
 * @author dzt
 *
 */
public class WeatherHandler extends DefaultHandler {
	String status, date, currentCity;
	String tagName;
	WeatherInfo weatherInfo = null;
	ArrayList<WeatherInfo> weatherInfos;
	int flag = 0;

	public ArrayList<WeatherInfo> getWeathers() {
		return weatherInfos;
	}

	public String getDate() {
		return date;
	}

	public String getCity() {
		return currentCity;
	}

	/**
	 * 开始解析xml文件
	 */
	public void startDocument() throws SAXException {
		weatherInfos = new ArrayList<WeatherInfo>();
		System.out.println("````````begin````````");
	}

	/**
	 * 解析xml文件结束
	 */
	public void endDocument() throws SAXException {
		System.out.println("````````end````````");
	}

	/**
	 * 开始解析标签
	 */
	public void startElement(String namespaceURI, String localName,
							 String qName, Attributes attr) throws SAXException {
		tagName = localName;
		if (localName.equals("CityWeatherResponse")) {
			System.out.println("start-----CityWeatherResponse");
			// 获取标签的全部属性
			for (int i = 0; i < attr.getLength(); i++) {
				System.out.println("startElement " + attr.getLocalName(i) + "="
						+ attr.getValue(i));
			}
		} else if (localName.equals("date")) {
			flag++;
			if (flag > 1) {
				weatherInfo = new WeatherInfo();
			}
		} else if (localName.equals("results")) {
			System.out.println("start-----results");
		} else if (localName.equals("weather_data")) {
			System.out.println("start-----weather_data");
		}
	}

	/**
	 * 读取到内容
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// System.out.println("characters---tagName = " + tagName);
		if (tagName != null) {
			String data = new String(ch, start, length);
			if (tagName.equals("status"))
				status = data;
			else if (tagName.equals("currentCity"))
				currentCity = data;
			else if (tagName.equals("date")) {
				if (flag == 1) {
					date = data;
				} else {
					weatherInfo.setDate(data);
				}
			} else if (tagName.equals("dayPictureUrl")) {
				weatherInfo.setDayPictureUrl(data);
			} else if (tagName.equals("nightPictureUrl")) {
				weatherInfo.setNightPictureUrl(data);
			} else if (tagName.equals("weather")) {
				weatherInfo.setWeather(data);
			} else if (tagName.equals("wind")) {
				weatherInfo.setWind(data);
			} else if (tagName.equals("temperature")) {
				weatherInfo.setTemperature(data);
			}
		}
	}

	/**
	 * 解析标签结束
	 */
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		// 在workr标签解析完之后，会打印出所有得到的数据
		tagName = "";
		if (localName.equals("CityWeatherResponse")) {
			System.out.println("end-----CityWeatherResponse");
			flag = 0;
			// printout();
		} else if (localName.equals("results")) {
			System.out.println("end-----results");
		} else if (localName.equals("weather_data")) {
			System.out.println("end-----weather_data");
		} else if (localName.equals("temperature")) {
			weatherInfos.add(weatherInfo);
		}
	}

	// 打开一个标签的内容
	public void printout() {
		System.out.println("status: " + status);
		System.out.println("date: " + date);
		System.out.println("currentCity: " + currentCity);
		for (int i = 0; i < weatherInfos.size(); i++) {
			System.out.println(weatherInfos.get(i).toString());
		}
		System.out.println();
	}

}

