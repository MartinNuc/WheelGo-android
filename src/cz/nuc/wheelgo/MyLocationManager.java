package cz.nuc.wheelgo;

import android.app.PendingIntent;
import android.content.Context;
import android.location.*;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocationManager  {

	Location poloha;
	LocationManager lm;
	
	MyLocationManager(LocationManager locationManager) {
		lm = locationManager;
	}

	LocationListener onLocationChange = new LocationListener() {
		public void onLocationChanged(Location location) {
			poloha = location;
		}

		public void onProviderDisabled(String provider) {
			// required for interface, not used
		}

		public void onProviderEnabled(String provider) {
			// required for interface, not used
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// required for interface, not used
		}
	};
	
	public void startGPS()
	{
		// watching position every 5 seconds or in at least 20m distance change
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 20, onLocationChange);
	}
	
	public void stopGPS()
	{
		lm.removeUpdates(onLocationChange);
	}
	
	public Location getLastKnownLocation()
	{
		poloha = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return poloha;
	}
	
	public void removeUpdates(PendingIntent pi)
	{
		lm.removeUpdates(pi);
	}
	
	/*
	 * Gets human readable current address information
	 */
	public Address getAddressForLocation(Context context, Location location) throws IOException {

        if (location == null) {
            return null;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        int maxResults = 1;

        Geocoder gc = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gc.getFromLocation(latitude, longitude, maxResults);

        if (addresses.size() == 1) {
            return addresses.get(0);
        } else {
            return null;
        }
    }
	
}
