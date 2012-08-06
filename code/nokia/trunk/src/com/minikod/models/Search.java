package com.minikod.models;

import com.minikod.utils.Util;
import com.minikod.utils.json.JSONException;
import com.minikod.utils.json.JSONObject;
/*
 * Class for Return data of places api
 */
public class Search {
	public Position position;
	public String type;

	/*
	 * Constants for JSONObject properties
	 */
	public static String positionStr = "position";
	public static String typeStr = "type";
	public static String contextStr = "context";
	public static String locationStr = "location";

	/*
	 * Constructor that creates class from JSOnObject
	 */
	public Search(JSONObject search) {
		if (search != null) {
			JSONObject context = null;
			try {
				context = search.getJSONObject(contextStr);
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}
			if (context != null) {

				/*
				 * Type
				 */
				try {
					setType(context.getString(typeStr));
				} catch (Exception e) {
					Util.logEIngonred(e);
				}

				JSONObject location = null;
				try {
					location = context.getJSONObject(locationStr);
				} catch (JSONException e) {
					Util.logEIngonred(e);
				}
				if (location != null) {

					/*
					 * Position
					 */
					try {
						setPosition(new Position(
								location.getJSONArray(positionStr)));
					} catch (Exception e) {
						Util.logEIngonred(e);
					}
				}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
