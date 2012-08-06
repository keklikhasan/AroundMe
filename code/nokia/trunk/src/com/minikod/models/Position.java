package com.minikod.models;

import javax.microedition.location.Location;

import com.minikod.utils.Util;
import com.minikod.utils.json.JSONArray;
import com.minikod.utils.json.JSONException;
import com.nokia.maps.common.GeoCoordinate;

/*
 *  Class keeps location information 
 */
public class Position {
	public double corX;
	public double corY;
	public GeoCoordinate cor;
	public Location loc;

	public Position(JSONArray positions) {
		if (positions != null) {
			/*
			 * corX
			 */
			try {
				setCorX(Double.parseDouble(positions.getString(0)));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}

			/*
			 * corY
			 */
			try {
				setCorY(Double.parseDouble(positions.getString(1)));
			} catch (JSONException e) {
				Util.logEIngonred(e);
			}
		}
	}
	
	public Position(double corX, double corY) {
		this.corX = corX;
		this.corY = corY;
	}
	
	/*
	 * Custom Getters & Setters
	 */
	
	public GeoCoordinate getCor() {
		return new GeoCoordinate(corX, corY, 0);
	}
	/*
	 * Getters & SetterS
	 */
	

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	
	public void setCor(GeoCoordinate cor) {
		this.cor = cor;
	}


	public double getCorX() {
		return corX;
	}

	public void setCorX(double corX) {
		this.corX = corX;
	}

	public double getCorY() {
		return corY;
	}

	public void setCorY(double corY) {
		this.corY = corY;
	}
}