package com.minikod.midlets;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.minikod.base.Base;
import com.minikod.utils.Util;

/*
 * Main midlet
 */
public class MapsMidlet extends MIDlet {

	protected void startApp() throws MIDletStateChangeException {
		// initialise the appId and token first
		Util.logI("initialase nokia api");
		Base.InitialiseAuth();
		/*
		 * create display and set Map
		 */
		Display display = Display.getDisplay(this);
		MapBase map = new MapBase(display, this);
		Util.logI("set MapBase");
		display.setCurrent(map);
	}

	public MapsMidlet() {

	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	protected void pauseApp() {

	}
}
