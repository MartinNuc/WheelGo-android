package cz.nuc.wheelgo;

import android.app.Activity;
import android.content.Intent;
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
import cz.nuc.wheelgo.reportingsystem.SpotsManager;
import cz.nuc.wheelgo.reportingsystem.Tip;

import java.io.IOException;
import java.util.Locale;

public class DetailTip extends Activity {

    public static final String DETAIL = "cz.nuc.wheelgo.DETAIL";
    private Integer id = null;

	private WheelGoApplication app = null;
	MyLocationManager lm;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailtip);

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
		// try to get id from data in extras
		if (id==null || id == -1)
        {
            this.finish();
            return;
        }

		SpotsManager sm = SpotsManager.getInstance();
		Tip tip = sm.getDetailTip(id);
		if (tip == null)
        {
            this.finish();
            return;
        }
        
        ImageView image = (ImageView)this.findViewById(R.id.Picture);
        image.setImageBitmap(sm.getImage(tip.getId()));
        TextView description = (TextView)this.findViewById(R.id.Describtion);
        description.setText(tip.getDescription());
        
		Location location = new Location("GPS");
		float latitude = (float) (tip.getCoordinates().latitude);
		float longitude = (float) (tip.getCoordinates().longitude);

		location.setLatitude(latitude);
		location.setLongitude(longitude);

		Address addresses = new Address(Locale.getDefault());
		try {
			addresses = lm.getAddressForLocation(this, location);
		} catch (IOException e) {
			return;
		}

		TextView t = (TextView) findViewById(R.id.txtLocation);
		for (int i = 0; i < addresses.getMaxAddressLineIndex(); i++)
			t.append(addresses.getAddressLine(i) + " ");

        
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
	    inflater.inflate(R.menu.detailtipmenu, menu);
		return true;
	}
	
	/**
	 * Menu selection
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.Edit:
	    	// edit tip
			final Intent editIntent = new Intent(this, ReportTip.class);
			editIntent.putExtra("id", Integer.toString(id));
			editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			this.startActivity(editIntent);
			this.finish();
	        return true;
	    case R.id.Delete:
	    	// delete tip
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
