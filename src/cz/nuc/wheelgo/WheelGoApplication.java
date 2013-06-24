package cz.nuc.wheelgo;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

public class WheelGoApplication extends Application {

	MyLocationManager lm;
	private boolean using_gps=false;
	public boolean isUsing_gps() {
		return using_gps;
	}
	
	@Override
	public void onCreate()
	{
		this.startGPS();
	}
	
	public MyLocationManager startGPS()
	{
		using_gps=true;
		if (lm!=null)
		{
			lm.startGPS();
			return lm;
		}
		lm = new MyLocationManager((LocationManager)getSystemService(Context.LOCATION_SERVICE));
		return lm;
	}
	
	public void stopGPS()
	{
		using_gps=false;
		(new removeLMThread()).start();
		return;
	}

	private class removeLMThread extends Thread {

		@Override
		public void run() {
			try {
				sleep(2500);
			} catch (Exception e) {
				// nothing
			}

			if (using_gps == false && lm != null) {
				lm.stopGPS();
				lm = null;
			}
		}
	}
	
}
