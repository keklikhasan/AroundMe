package com.minikod.models;

import java.util.Vector;

import com.minikod.utils.Util;
import com.minikod.utils.json.JSONArray;
import com.minikod.utils.json.JSONException;
import com.minikod.utils.json.JSONObject;

/*
 * Keeps all data that reurn from api
 */
public class Places {

	public Vector places;
	public Search search;

	/*
	 * Constructor that creates object from JSONobject and JSONArray
	 */
	public Places(JSONArray places, JSONObject search) {
		/*
		 * create searc
		 */
		setSearch(new Search(search));

		/*
		 * create places
		 */
		if (places != null && places.length() > 0) {
			this.places = new Vector();
			for (int i = 0; i < places.length(); i++) {
				try {
					JSONObject obj = places.getJSONObject(i);
					Place pl = new Place(obj);
					this.places.addElement(pl);
				} catch (JSONException e) {
					Util.logEIngonred(e);
				}
			}
		}
	}

	public Vector getPlaces() {
		return places;
	}

	public void setPlaces(Vector places) {
		this.places = places;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

}
