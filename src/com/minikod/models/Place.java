package com.minikod.models;

import com.minikod.utils.Util;
import com.minikod.utils.json.JSONException;
import com.minikod.utils.json.JSONObject;

public class Place {

	public Position position;
	public Category category;
	public double distance;
	public String title;
	public double averageRating;
	public String iconUrl;
	public String vicinity;
	public String having;
	public String type;
	public String href;
	public String id;
	
	
	/*
	 * Constants for JSONObject properties
	 */
	public static String positionStr="position";
	public static String distanceStr="distance";
	public static String titleStr="title";
	public static String averageRatingStr="averageRating";
	public static String iconUrlStr="icon";
	public static String vicinityStr="vicinity";
	public static String havingStr="having";
	public static String typeStr="type";
	public static String hrefStr="href";
	public static String idStr="id";
	public static String categoryStr="category";

	/*
	 * Constructor that creates class from JSOnObject
	 */
	public Place(JSONObject place) {
		if (place != null ) {
			
			/*
			 * Position
			 */
			try {
				setPosition(new Position(place.getJSONArray(positionStr)));
			} catch (Exception e) {
				Util.logEIngonred(e);
			}
			
			/*
			 * Category
			 */
			try {
				setCategory(new Category(place.getJSONObject(categoryStr)));
			} catch (Exception e) {
				Util.logEIngonred(e);
			}
			
			/*
			 * Title
			 */
			try {
				setTitle(place.getString(titleStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * Distance
			 */
			try {
				setDistance(Double.parseDouble(place.getString(distanceStr)));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * AverageRating
			 */
			try {
				setAverageRating(Double.parseDouble(place
						.getString(averageRatingStr)));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * iconUrl
			 */
			try {
				setIconUrl(place
						.getString(iconUrlStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}
			
			/*
			 * Vicinity
			 */
			try {
				setVicinity(place
						.getString(vicinityStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}
			
			/*
			 * Having
			 */
			try {
				setHaving(place
						.getString(havingStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}
			
			/*
			 * Type
			 */
			try {
				setType(place
						.getString(typeStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}
			

			/*
			 * Href
			 */
			try {
				setHref(place
						.getString(hrefStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * Id
			 */
			try {
				setId(place
						.getString(idStr));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}


		}
	}

	/*
	 * Getter & Setter Methods
	 */

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public String getHaving() {
		return having;
	}

	public void setHaving(String having) {
		this.having = having;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
