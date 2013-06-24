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
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import cz.nuc.wheelgo.reportingsystem.Duration;
import cz.nuc.wheelgo.reportingsystem.Problem;
import cz.nuc.wheelgo.reportingsystem.Spot;
import cz.nuc.wheelgo.reportingsystem.SpotsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailProblem extends Activity {

    public static final String DETAIL = "cz.nuc.wheelgo.DETAIL";
	private Integer id = null;
	
	private WheelGoApplication app = null;
	MyLocationManager lm;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailproblem);
        
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
        this.id = getIntent().getIntExtra(DETAIL, -1);
        // try to get id from data in extras
        if (this.id==null || this.id == -1)
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
	    inflater.inflate(R.menu.detailproblemmenu, menu);
		return true;
	}
	
	/**
	 * Menu selection
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.Edit:
	    	// edit problem
			final Intent editIntent = new Intent(this, ReportProblem.class);
            editIntent.putExtra(ReportProblem.ID, id);
			editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			this.startActivity(editIntent);
			this.finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
    
    public void Delete_onClick(View v)
    {
        new DeleteRecordTask().execute(id);
    }
    
    public void Back_onClick(View v)
    {
    	this.finish();
    }

    class LoadDetailTask extends AsyncTask<Integer, Void, Problem> {

        @Override
        protected Problem doInBackground(Integer... params) {
            SpotsManager sm = SpotsManager.getInstance();
            return sm.getDetailProblem(params[0]);
        }

        @Override
        protected void onPostExecute(Problem problem) {

            if (problem == null)
            {
                finish();
                return;
            }

            TextView txtDuration = (TextView)findViewById(R.id.txtDuration);
            TextView txtDurationDesc = (TextView)findViewById(R.id.txtDurationDesc);
            Date duration = new Date(problem.getDuration().getTime());
            if (duration==null || duration.equals(new Date(0,0,0)))
            {
                txtDurationDesc.setVisibility(TextView.INVISIBLE);
                txtDuration.setText("Dlouhodobé");
            }
            else
            {
                txtDurationDesc.setVisibility(TextView.VISIBLE);
                // date is reasonable -> we have to decode it
                duration.setTime(duration.getTime()-(new Date()).getTime());
                txtDuration.setText(Duration.DateToString(duration));
            }

            TextView description = (TextView)findViewById(R.id.Describtion);
            description.setText(problem.getDescription());

            Location location = new Location("GPS");
            float latitude = (float) (problem.getCoordinates().latitude);
            float longitude = (float) (problem.getCoordinates().longitude);

            location.setLatitude(latitude);
            location.setLongitude(longitude);

            Address addresses = new Address(Locale.getDefault());
            try {
                addresses = lm.getAddressForLocation(getApplicationContext(), location);
            } catch (IOException e) {
                return;
            }

            new LoadImageTask().execute(problem.getId());

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
