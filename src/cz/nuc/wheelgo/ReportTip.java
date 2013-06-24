package cz.nuc.wheelgo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.google.android.gms.maps.model.LatLng;
import cz.nuc.wheelgo.reportingsystem.Problem;
import cz.nuc.wheelgo.reportingsystem.SpotsManager;
import cz.nuc.wheelgo.reportingsystem.Tip;

import java.io.IOException;
import java.util.Locale;

public class ReportTip extends Activity {

	private Bitmap photo;
	private Integer id;

	private WheelGoApplication app = null;

	MyLocationManager lm;
	Location location = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporttip);

		// initialize location manager
		app = (WheelGoApplication) this.getApplication();

		Button odeslat = (Button) this.findViewById(R.id.Send);
		odeslat.getBackground().setColorFilter(
				new LightingColorFilter(0xFFCCDDFF, 0xFF00FF00));
	}

	public void Report_onClick(View v) {
		EditText description = (EditText) (this.findViewById(R.id.editDescribtion));

		location = lm.getLastKnownLocation();
		LatLng current_position = new LatLng(
				location.getLatitude(),
				location.getLongitude());

		Tip newOne;
		if (id != null)
			newOne = new Tip(id);
		else
			newOne = new Tip();

		newOne.setCoordinates(current_position);
		newOne.setDescribtion(description.getText().toString());
		newOne.setPhoto(this.photo);
		SpotsManager sm = SpotsManager.getInstance();
		if (id != null)
			sm.editTip(newOne);
		else
            new BackgroundAsyncTask().execute(newOne);

		this.finish();
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
			Toast.makeText(this, R.string.photo_not_taken, Toast.LENGTH_SHORT);
		else {
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
		final Bundle extras = getIntent().getExtras();
		// try to get id from data in extras
		if (id == null && extras != null) {
			String str = extras.getString("id");
			id = Integer.parseInt(str);
		}

		Location location;
		if (id != null) {
			// editting
			SpotsManager sm = SpotsManager.getInstance();
			Tip tip = sm.getDetailTip(id);
			if (tip == null)
				this.finish();

			location = new Location("GPS");
			double latitude = tip.getCoordinates().latitude;
			double longitude = tip.getCoordinates().longitude;

			location.setLatitude(latitude);
			location.setLongitude(longitude);

			TextView t = (TextView) findViewById(R.id.editDescribtion);
			t.setText(tip.getDescription());

			// is there any photo?
			this.photo = sm.getImage(tip.getId());
			if (this.photo != null) {
				ImageButton imageButton = (ImageButton) findViewById(R.id.cameraButton);
				imageButton.setImageBitmap(this.photo);
			}
		} else
			// new
			location = lm.getLastKnownLocation();

		Address addresses = new Address(Locale.getDefault());
		try {
			addresses = lm.getAddressForLocation(this, location);
		} catch (IOException e) {
			return;
		}

		TextView t = (TextView) findViewById(R.id.txtLocation);
		for (int i = 0; i < addresses.getMaxAddressLineIndex(); i++)
			t.append(addresses.getAddressLine(i) + " ");
	}

	/**
	 * Take care about GPS
	 */
	@Override
	public void onPause() {
		app.stopGPS();
		super.onStop();
	}

    public class BackgroundAsyncTask extends AsyncTask<Tip, String, Void> {

        @Override
        protected Void doInBackground(Tip... params) {
            SpotsManager sm = SpotsManager.getInstance();
            sm.reportTip(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            finish();


        }

    }
}