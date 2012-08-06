package com.minikod.midlets;

import java.util.Vector;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.midlet.MIDlet;

import com.minikod.base.Base;
import com.minikod.models.Place;
import com.minikod.models.Places;
import com.minikod.service.NokiaPlacesApiService;
import com.minikod.utils.Util;
import com.nokia.maps.common.GeoCoordinate;
import com.nokia.maps.map.MapDisplayState;
import com.nokia.maps.map.MapObject;
import com.nokia.maps.map.MapStandardMarker;
import com.nokia.maps.map.Point;

/*
 * Custom map canvas
 */
public class MapBase extends Base implements LocationListener {

	/*
	 * Commands
	 */
	// menu to open details of selected place
	private final Command SHOW_PLACE_DETAILS = new Command("Details",
			Command.ITEM, 3);
	// menu to open summary of selected place
	private final Command SHOW_PLACE_SUMMARY = new Command("Summary",
			Command.ITEM, 2);
	// button for search a place
	private final Command SEARCH = new Command("Search", Command.OK, 3);
	// button for look places near by
	private final Command LOOK = new Command("Look", Command.OK, 2);
	// OK button
	private final Command OK = new Command("Ok", Command.OK, 1);

	/*
	 * TextBoxs
	 */
	private final TextBox searchBox = new TextBox("Enter search term", "Hotel",
			100, TextField.ANY);

	/*
	 * Variables
	 */
	private Place currentPlace;
	private Vector places;
	private Location curentLoc;
	private MapStandardMarker marker;
	private static int ZOOM_LEVEL = 15; // zoom level default 15

	/*
	 * Constants
	 */
	private static final int MY_MARKER_SIZE = 24; // size of triangle marker
													// that points where are we
	private static final int PLACE_MARKER_SIZE = 8; // size of baloon marker
													// that points wher places
													// are
	private static final int ACCURACY = 5000; // accuracy fro location provider
	private static final int MAP_INTERVAL_TIME = 60;// interval time that
													// location update
	private static final int MAP_GETLOCATION_TIME_OUT = 60;// timeout for geting
															// location

	/*
	 * Constructor
	 */
	public MapBase(Display display, MIDlet midlet) {
		super(display, midlet);
		Util.logI("application started");
		/*
		 * try get current location
		 */
		progressStart("Getting Current Location",
				"An error occoured while trying to get location");
		ProcessGetLocation pgl = new ProcessGetLocation(this);
		pgl.start();
		/*
		 * add buttons
		 */
		addCommand(SEARCH);
		addCommand(LOOK);
		Util.logI("add Updater");
		map.addMapComponent(new Updater());
	}

	/*
	 * Set Current Location and mark it triagnle
	 */
	public void setCurentLoc(Location currentLocation) {
		this.curentLoc = currentLocation;
		try {
			if (map != null && mapFactory != null) {
				if (marker != null) {
					map.removeMapObject(marker);
				}
				marker = mapFactory.createStandardMarker(new GeoCoordinate(
						curentLoc.getQualifiedCoordinates().getLatitude(),
						curentLoc.getQualifiedCoordinates().getLongitude(),
						curentLoc.getQualifiedCoordinates().getAltitude()),
						MY_MARKER_SIZE, "currentLocation",
						MapStandardMarker.TRIANGLE);
				map.addMapObject(marker);
				Util.logI("My location marker set");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Util.logE(e.toString());
		}

	}

	/*
	 * update map change map focus draw places
	 */
	public void updateMap(Location loc) {
		Util.logI("update Map executed");
		setCurentLoc(loc);
		// set places on map
		if (places != null && places.size() > 0) {
			for (int i = 0; i < places.size(); i++) {
				Util.logI("draw " + i);
				Place pl = (Place) places.elementAt(i);
				if (pl != null && pl.getPosition() != null) {
					Util.logI("draw " + pl.getId() + " - "
							+ pl.getPosition().getCorX() + ","
							+ pl.getPosition().getCorY());
					MapStandardMarker marker = mapFactory.createStandardMarker(
							pl.getPosition().getCor(), PLACE_MARKER_SIZE,
							pl.getId(), MapStandardMarker.BALLOON);
					map.addMapObject(marker);
				}
			}
		}
		Util.logI("update Map finished");
	}

	/*
	 * handle command
	 */
	protected void commandRun(Command c) {
		if (c == SHOW_PLACE_SUMMARY) {
			Util.logI("command run - SHOW_PLACE_SUMMARY");
			displayPlaceSummary();
		} else if (c == SHOW_PLACE_DETAILS) {
			Util.logI("command run - SHOW_PLACE_DETAILS");
			displayPlaceDetails();
		} else if (c == SEARCH) {
			Util.logI("command run - SEARCH");
			map.removeAllMapObjects();
			searchBox.addCommand(OK);
			searchBox.setCommandListener(this);
			display.setCurrent(searchBox);
		} else if (c == LOOK) {
			Util.logI("command run - LOOK");
			map.removeAllMapObjects();
			progressStart("Getting Places near you",
					"An error occoured while trying to get places");
			ProcessGetPlaces pum = new ProcessGetPlaces(this);
			pum.type = false;
			pum.start();
		} else if (c == OK) {
			Util.logI("command run - OK");
			progressStart("Searching places",
					"An error occoured while Searching  places");
			ProcessGetPlaces pum = new ProcessGetPlaces(this);
			pum.type = true;
			pum.start();
		} else {
			Util.logI("command run - OTHER");
		}
	}

	/*
	 * Shows information about a found place.
	 */
	private void displayPlaceSummary() {
		Util.logI("Show current place summary");
		String name = currentPlace.getTitle();
		String text = "";

		if (null != currentPlace.getTitle()) {
			text += "Title: " + currentPlace.getTitle() + "\n";
		}
		if (null != currentPlace.getVicinity()) {
			text += "Vicinity: " + currentPlace.getVicinity() + "\n";
		}
		if (null != currentPlace.getHref()) {
			text += "Url: " + currentPlace.getHref() + "\n";
		}
		Alert a = new Alert(name, text, null, AlertType.INFO);
		a.setTimeout(Alert.FOREVER);
		display.setCurrent(a, this);

	}

	/*
	 * Navigate the selected place web adres
	 */
	private void displayPlaceDetails() {
		try {
			Util.logI("Show current place details");
			midlet.platformRequest(currentPlace.getHref());
		} catch (ConnectionNotFoundException ce) {
			error(ce.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.location.LocationListener#locationUpdated(javax
	 * .microedition.location.LocationProvider,
	 * javax.microedition.location.Location)
	 */
	public void locationUpdated(LocationProvider arg0, Location loc) {
		Util.logI("Locaiton updated");
		setCurentLoc(loc);
	}

	public void providerStateChanged(LocationProvider arg0, int arg1) {
		Util.logI("Location State Changed");
	}

	/*
	 * Method for focusing map to given location
	 */
	public void focus(GeoCoordinate loc, int ZOOM_LEVEL) {
		try {
			if (map != null)
				map.setState(new MapDisplayState(new GeoCoordinate(loc
						.getLatitude(), loc.getLongitude(), loc.getAltitude()),
						ZOOM_LEVEL));
		} catch (Exception e) {
			e.printStackTrace();
			error("An error occoured while trying to set location detail : "
					+ e.toString());
		}
	}

	/*
	 * Back Processes
	 */

	/*
	 * get places near by or search
	 */
	public class ProcessGetPlaces implements Runnable {
		public MapBase mapBase;
		public boolean type = false;

		/*
		 * false -> get places near me true -> search places
		 */

		public ProcessGetPlaces(MapBase mapBase) {
			this.mapBase = mapBase;
		}

		public void run() {

			Util.logI("process get places near by");
			display.setCurrent(mapBase);
			removeCommand(SEARCH);
			removeCommand(LOOK);
			Places places = null;

			/*
			 * get places
			 */
			try {
				if (type) {
					/*
					 * search
					 */
					places = NokiaPlacesApiService.getPlaces(curentLoc
							.getQualifiedCoordinates().getLatitude() + "",
							curentLoc.getQualifiedCoordinates().getLongitude()
									+ "", searchBox.getString());
				} else {
					/*
					 * look
					 */
					places = NokiaPlacesApiService.getPlaces(curentLoc
							.getQualifiedCoordinates().getLatitude() + "",
							curentLoc.getQualifiedCoordinates().getLongitude()
									+ "");
				}

				if (null == places || null == places.getPlaces()
						|| places.getPlaces().size() < 1) {
					if (type) {
						error("There aren't any hotel near you");
					} else {
						error("There aren't any hotel you're lokking for");
					}
					return;
				}

				mapBase.places = places.places;
				if (type) {
					/*
					 * focus on nearest place from seacrh
					 */
					try {
						Place pl = (Place) places.getPlaces().elementAt(0);
						focus(pl.getPosition().getCor(), ZOOM_LEVEL);
					} catch (Exception e) {
						e.printStackTrace();
						Util.logE(e.toString());
					}
				} else {
					/*
					 * focus where we are
					 */
					focus(new GeoCoordinate(curentLoc.getQualifiedCoordinates()
							.getLatitude(), curentLoc.getQualifiedCoordinates()
							.getLongitude(), curentLoc
							.getQualifiedCoordinates().getAltitude()),
							ZOOM_LEVEL);
				}
				Util.logI("process get places finished");
				updateMap(curentLoc);
			} catch (Exception e) {
				e.printStackTrace();
				error("An error occoured while trying to get places data detail : "
						+ e.toString());
			} finally {
				progressEnd();
				addCommand(SEARCH);
				addCommand(LOOK);
			}
		}

		public void start() {
			try {
				Thread thread = new Thread(this);
				thread.start();
			} catch (Exception e) {
				Util.logEIngonred(e);
			}
		}

	}

	public class ProcessGetLocation implements Runnable {
		public MapBase mapBase;

		public ProcessGetLocation(MapBase mapBase) {
			this.mapBase = mapBase;
		}

		public void run() {
			removeCommand(SEARCH);
			removeCommand(LOOK);
			/*
			 * Criteria for getting locationProivder
			 */
			Criteria ct = new Criteria();
			ct.setCostAllowed(true);
			ct.setPreferredPowerConsumption(Criteria.NO_REQUIREMENT);
			ct.setVerticalAccuracy(ACCURACY);
			ct.setHorizontalAccuracy(ACCURACY);

			LocationProvider locP = null;
			try {
				// get locationProivder
				locP = LocationProvider.getInstance(ct);
				if (locP != null) {
					Util.logI("LocationProvider created");
					// set location Listener
					locP.setLocationListener(mapBase, MAP_INTERVAL_TIME, -1, -1);
					Location loc = locP.getLocation(MAP_GETLOCATION_TIME_OUT);
					if (loc != null) {
						Util.logI("get Location");
						if (curentLoc == null) {
							Util.logI("curr loc is null set curr loc");
							setCurentLoc(loc);
						}
						/*
						 * Set map state to current location
						 */
						focus(new GeoCoordinate(loc.getQualifiedCoordinates()
								.getLatitude(), loc.getQualifiedCoordinates()
								.getLongitude(), loc.getQualifiedCoordinates()
								.getAltitude()), ZOOM_LEVEL);
						/*
						 * upadte Map get Places list
						 */
						progressEnd();
						updateMap(loc);
					} else {
						error("An error occoured while trying to get location ");
						Util.logE("An error occoured while trying to get location ");
					}
				} else {
					error("An error occoured while trying to get location ");
					Util.logE("An error occoured while trying to get location ");
				}
			} catch (Exception e) {
				e.printStackTrace();
				error("An erOror occoured while trying to get location detail : "
						+ e.toString());
				Util.logE("An error occoured while trying to get location detail : "
						+ e.toString());
			} finally {
				progressEnd();
				addCommand(SEARCH);
				addCommand(LOOK);
			}
		}

		public void start() {
			try {
				Thread thread = new Thread(this);
				thread.start();
			} catch (Exception e) {
				Util.logEIngonred(e);
			}
		}
	}

	/*
	 * Map updater
	 */

	public Place findPlaceFromPlaces(MapStandardMarker mo) {

		String nameOfPlace = mo.getText();
		Util.logI("find marker " + nameOfPlace);
		if (nameOfPlace == null) {
			return null;
		}
		if (places != null && places.size() > 0) {
			for (int i = 0; i < places.size(); i++) {
				Place pl = (Place) places.elementAt(i);
				if (pl.getId().equals(nameOfPlace)) {
					Util.logI("finded marker " + nameOfPlace);
					return pl;
				}
			}
		}
		Util.logI("noy founded marker " + nameOfPlace);
		return null;
	}

	/*
	 * Checks to see if the cursor is above a place and updates the commands
	 * accordingly
	 */
	public class Updater extends MapComponentImpl {

		private Point center = new Point(map.getWidth() / 2,
				map.getHeight() / 2);

		public void mapUpdated(boolean zoomChanged) {
			setCurentLoc(curentLoc);
			Util.logI("updater map updated");
			MapObject mo = map.getObjectAt(center);
			boolean objectUnderCurson = false;

			if (mo != null && mo instanceof MapStandardMarker) {
				Util.logI("try to find curent place");
				currentPlace = findPlaceFromPlaces((MapStandardMarker) mo);
				objectUnderCurson = (currentPlace != null);
				Util.logI("marker found  " + objectUnderCurson);
				// This brings the object to the front
				map.removeMapObject(mo);
				map.addMapObject(mo);
			}
			if (objectUnderCurson) {
				Util.logI("remove search and look");
				removeCommand(SEARCH);
				removeCommand(LOOK);
				Util.logI("add summarry and detail");
				addCommand(SHOW_PLACE_SUMMARY);
				addCommand(SHOW_PLACE_DETAILS);
			} else {
				handleCursorOffPlace();
				Util.logI("no place selected");
			}
		}

		private void handleCursorOffPlace() {
			// no place under cursor
			currentPlace = null;
			removeCommand(SHOW_PLACE_SUMMARY);
			removeCommand(SHOW_PLACE_DETAILS);
			addCommand(SEARCH);
			addCommand(LOOK);
		}
	}
}
