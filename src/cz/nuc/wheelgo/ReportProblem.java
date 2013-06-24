package cz.nuc.wheelgo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.android.gms.maps.model.LatLng;
import cz.nuc.wheelgo.reportingsystem.Duration;
import cz.nuc.wheelgo.reportingsystem.Problem;
import cz.nuc.wheelgo.reportingsystem.SpotsManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReportProblem extends Activity {

    public static final String ID = "cz.nuc.wheelgo.id" ;
    private Bitmap photo;
	private Integer id;

	private WheelGoApplication app = null;

	MyLocationManager lm;
	Location location = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportproblem);

		// initialize location manager
		app = (WheelGoApplication) this.getApplication();

		Button odelat = (Button) this.findViewById(R.id.Send);
		odelat.getBackground().setColorFilter(
				new LightingColorFilter(0xFFCCDDFF, 0xFF00FF00));

		Spinner s = (Spinner) findViewById(R.id.comboDuration);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.lasts, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);

	}

	public void Report_onClick(View v) {
		EditText description = (EditText) (this.findViewById(R.id.editDescribtion));

		location = lm.getLastKnownLocation();
		LatLng currentLocation = new LatLng(
				location.getLatitude(),
				location.getLongitude());

		Problem newOne;
		if (id!=null)
			newOne = new Problem(id);
		else
			newOne = new Problem();
		
		newOne.setCoordinates(currentLocation);
		newOne.setDescribtion(description.getText().toString());
		newOne.setPhoto(this.photo);
		
		Spinner s = (Spinner) findViewById(R.id.comboDuration);
		
		Date duration = new Date();
		int pos = s.getSelectedItemPosition();
		switch (pos)
		{
			case 0:
				duration.setTime(duration.getTime() +3*1000*3600);
				break;
			case 1:
				duration.setTime(duration.getTime() +6*1000*3600);
				break;
			case 2:
				duration.setTime(duration.getTime() +12*1000*3600);
				break;
			case 3:
				duration.setTime(duration.getTime() +167*1000*3600);
				break;
			case 4:
				duration.setTime(duration.getTime() +336*1000*3600);
				break;
			case 5:
				duration.setTime(duration.getTime() +774*1000*3600);  // TO-DO tady to pretece ... potreba vymyslet, jak k nynejsimu datu pridat mesic navic
				break;
			case 6:
				duration.setTime(0);
		}
		newOne.setDuration(duration);

		if (id==null)
        {
            new BackgroundAsyncTask(false).execute(newOne);
        }
		else
        {
			new BackgroundAsyncTask(true).execute(newOne);
        }
	}

	/**
	 * starts default camera application
	 * 
	 * @param v
	 */
	public void TakeShot_onClick(View v) {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, 1);
	}

	/**
	 * Catches result after taking a photo by camera
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED)
			Toast.makeText((Context)this, R.string.photo_not_taken,
					Toast.LENGTH_SHORT);
		else
		{
			this.photo = (Bitmap) data.getExtras().get("data");
			ImageButton imageButton = (ImageButton) findViewById(R.id.cameraButton);
			imageButton.setImageBitmap(this.photo);
		}
	}

	public void Back_onClick(View v) {
		this.finish();
	}

	@Override
	public void onResume() {
		super.onResume();
		lm = app.startGPS();
		
		// get parameters
        this.id = getIntent().getIntExtra(ID, -1);
        if (id == -1)
            id = null;

		if (id==null) {
			location = lm.getLastKnownLocation();
        }
        else
        {
            // edit
            new LoadDetailTask().execute(id);
        }
		
	}

	/**
	 * Take care about GPS
	 */
	@Override
	public void onPause() {
		app.stopGPS();
		super.onStop();
	}


    public class BackgroundAsyncTask extends AsyncTask<Problem, String, Void> {

        boolean editing = false;

        public BackgroundAsyncTask(boolean editing)
        {
            this.editing = editing;
        }

        @Override
        protected Void doInBackground(Problem... params) {
            SpotsManager sm = SpotsManager.getInstance();
            if (editing == false)
                sm.reportProblem(params[0]);
            else
                sm.editProblem(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            finish();

        }
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

            location = new Location("GPS");
            float latitude = (float) (problem.getCoordinates().latitude);
            float longitude = (float) (problem.getCoordinates().longitude);

            location.setLatitude(latitude);
            location.setLongitude(longitude);

            TextView t = (TextView) findViewById(R.id.editDescribtion);
            t.setText(problem.getDescription());

            Spinner s = (Spinner) findViewById(R.id.comboDuration);

            if (problem.getDuration().equals(new Date(0,0,0,0,0)))
            {
                s.setSelection(7);
            }
            else
            {
                Date duration = new Date(problem.getDuration().getTime() - new Date().getTime());
                String difference = Duration.DateToString(duration);
                if (difference== "3 hodin")
                    s.setSelection(0);
                else if (difference == "6 hodin")
                    s.setSelection(1);
                else if (difference == "12 hodin")
                    s.setSelection(2);
                else if (difference == "1 t�den")
                    s.setSelection(3);
                else if (difference == "14 dn�")
                    s.setSelection(4);
                else if (difference == "1 m�s�c")
                    s.setSelection(5);
                else if (difference == "D�le ne� m�s�c")
                    s.setSelection(6);
            }

            new LoadImageTask().execute(problem.getId());

            Address addresses = new Address(Locale.getDefault());
            try {
                addresses = lm.getAddressForLocation(getApplicationContext(), location);
            } catch (IOException e) {
                return;
            }

            if (addresses != null)
            {
                TextView txtLocation = (TextView) findViewById(R.id.txtLocation);
                for (int i = 0; i < addresses.getMaxAddressLineIndex(); i++)
                    txtLocation.append(addresses.getAddressLine(i) + " ");
            }


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

            ImageButton imageButton = (ImageButton) findViewById(R.id.cameraButton);
            imageButton.setImageBitmap(result);
            // is there any photo?
            photo = result;
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
