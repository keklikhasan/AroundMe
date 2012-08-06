package com.minikod.service;

import java.io.InputStream;

import javax.microedition.io.Connector;

import com.minikod.models.Places;
import com.minikod.utils.Util;
import com.minikod.utils.json.JSONArray;
import com.minikod.utils.json.JSONObject;

/**
 * 
 * @author hkeklik
 * 
 *         this class have methods that retirve data from nokia places api
 */
public class NokiaPlacesApiService {

	/*
	 * Constants
	 */
	public static String resultsStr = "results";
	public static String itemsStr = "items";
	public static String searchStr = "search";
	public static String iconStr = "icon";

	/*
	 * App information
	 */
	public static String appId = "g6gVI6JE9DCj52eoXRHN";
	public static String appCode = "8v5Hhb4aFQj2x7DqUX-XOw";

	/*
	 * Url patern
	 */
	public static String urlPart1 = "http://demo.places.nlp.nokia.com/places/v1/discover/explore?&at=";
	public static String urlPart2 = "&app_id=" + appId + "&app_code=" + appCode
			+ "&tf=plain" + "&pretty=true" + "&cat=hotel";
	public static String urlPart3 = "&q=";

	/*
	 * Get nearest places to given location
	 */
	public static Places getPlaces(String corX, String corY) throws Exception {

		/*
		 * Create url for location
		 */
		String url = urlPart1 + corX + "," + corY + urlPart2;
		Util.logI(url);
		InputStream is = null;
		try {
			/*
			 * Get content of data
			 */
			is = Connector.openInputStream(url);
			String resultStr = Util.convertInputStreamToString(is);

			JSONObject data = new JSONObject(resultStr);
			// get place item list object
			JSONObject result = data.getJSONObject(resultsStr);
			// get place item list
			JSONArray items = result.getJSONArray(itemsStr);
			// get search value
			JSONObject search = data.getJSONObject(searchStr);
			return new Places(items, search);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * search nearest places to given location by criteria
	 */
	public static Places getPlaces(String corX, String corY, String criteria)
			throws Exception {

		/*
		 * Create url for location
		 */
		String url = urlPart1 + corX + "," + corY + urlPart3 + criteria
				+ urlPart2;
		Util.logI(url);
		InputStream is = null;
		try {
			/*
			 * Get content of data
			 */
			is = Connector.openInputStream(url);
			String resultStr = Util.convertInputStreamToString(is);

			JSONObject data = new JSONObject(resultStr);
			// get place item list object
			JSONObject result = data.getJSONObject(resultsStr);
			// get place item list
			JSONArray items = result.getJSONArray(itemsStr);
			// get search value
			JSONObject search = data.getJSONObject(searchStr);
			return new Places(items, search);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Get category icon
	 */
	public static String getCategoryIcon(String url) throws Exception {
		InputStream is = null;
		try {
			/*
			 * Get content of data
			 */
			is = Connector.openInputStream(url);
			String resultStr = Util.convertInputStreamToString(is);
			JSONObject data = new JSONObject(resultStr);
			return data.getString(iconStr);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
