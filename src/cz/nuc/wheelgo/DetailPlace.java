package cz.nuc.wheelgo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cz.nuc.wheelgo.reportingsystem.Duration;
import cz.nuc.wheelgo.reportingsystem.Place;
import cz.nuc.wheelgo.reportingsystem.Problem;
import cz.nuc.wheelgo.reportingsystem.SpotsManager;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class DetailPlace extends Activity {

    public static final String DETAIL = "cz.nuc.wheelgo.detail";
    private Integer id = null;

	private WheelGoApplication app = null;
	MyLocationManager lm;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailplace);
		// initialize location manager
		app = (WheelGoApplication) this.getApplication();
		lm = app.startGPS();
    }

    @Override
    public void onResume()
    {
		// initialize location manager
		app = (WheelGoApplication) this.getApplication();
		lm = app.startGPS();

		// get parameters
		Integer id = getIntent().getIntExtra(DETAIL, -1);
        if (id == null || id == -1)
        {
            this.finish();
            return;
        }

        new LoadDetailTask().execute(this.id);

        super.onResume();
    }
    
	/**
	 * Take care about GPS
	 */
	@Override
	public void onStart() {
		app.startGPS();
		super.onStart();
	}

	/**
	 * Take care about GPS
	 */
	@Override
	public void onPause() {
		app.stopGPS();
		super.onStop();
	}
    
	/************** MENU **************/
	/**
	 * Creating menu from xml
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.detailplacemenu, menu);
		return true;
	}

	/**
	 * Menu selection
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.Edit:
	    	// edit place
			final Intent editIntent = new Intent(this, ReportPlace.class);
			editIntent.putExtra("id", Integer.toString(id));
			editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			this.startActivity(editIntent);
			this.finish();
	        return true;
	    case R.id.Delete:
	    	// delete place
	    	Delete_onClick();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
    
    public void Delete_onClick()
    {
        new DeleteRecordTask().execute(id);
    }
    
    public void Back_onClick(View v)
    {
    	this.finish();
    }

    class LoadDetailTask extends AsyncTask<Integer, Void, Place> {

        @Override
        protected Place doInBackground(Integer... params) {
            SpotsManager sm = SpotsManager.getInstance();
            return sm.getDetailPlace(params[0]);
        }

        @Override
        protected void onPostExecute(Place place) {

            if (place == null)
            {
                finish();
                return;
            }

            TextView type = (TextView)findViewById(R.id.Place_type);

            TextView description = (TextView)findViewById(R.id.Describtion);
            description.setText(place.getDescription());

            Location location = new Location("GPS");
            float latitude = (float) (place.getCoordinates().latitude);
            float longitude = (float) (place.getCoordinates().longitude);

            location.setLatitude(latitude);
            location.setLongitude(longitude);

            Address addresses = new Address(Locale.getDefault());
            try {
                addresses = lm.getAddressForLocation(getApplicationContext(), location);
            } catch (IOException e) {
                return;
            }

            TextView t = (TextView) findViewById(R.id.txtLocation);
            for (int i = 0; i < addresses.getMaxAddressLineIndex(); i++)
                t.append(addresses.getAddressLine(i) + " ");
        }
    }

    class LoadImageTask extends AsyncTask<Integer, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Integer... params) {
            SpotsManager sm = SpotsManager.getInstance();
            return sm.getImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result == null)
                return;

            ImageView image = (ImageView)findViewById(R.id.Picture);
            image.setImageBitmap(result);

        }
    }

    class DeleteRecordTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            SpotsManager sm = SpotsManager.getInstance();
            sm.deleteSpot(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            finish();
        }
    }


}

