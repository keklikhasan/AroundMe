package com.minikod.models;

import com.minikod.service.NokiaPlacesApiService;
import com.minikod.utils.Util;
import com.minikod.utils.json.JSONException;
import com.minikod.utils.json.JSONObject;

public class Category {
	public String id;
	public String title;
	public String href;
	public String type;
	public String iconUrl;

	/*
	 * Constants for JSONObject properties
	 */
	public static String idStr = "id";
	public static String titleStr = "title";
	public static String hrefStr = "href";
	public static String typeStr = "type";

	/*
	 * Constructor that creates class from JSOnObject
	 */
	public Category(JSONObject category) {
		if (category != null) {
			/*
			 * Id
			 */
			try {
				setId(category.getString(idStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * Title
			 */
			try {
				setTitle(category.getString(titleStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * Type
			 */
			try {
				setType(category.getString(typeStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * Href
			 */
			try {
				setHref(category.getString(hrefStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * IconUrl
			 */
			if (getHref() != null) {
				try {
					setIconUrl(NokiaPlacesApiService.getCategoryIcon(getHref()));
				} catch (Exception e) {
					Util.logEIngonred(e);
				}
			}
		}
	}

	/*
	 * Getter & Setter Methods
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

}