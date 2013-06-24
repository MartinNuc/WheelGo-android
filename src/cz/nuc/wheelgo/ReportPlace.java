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
import cz.nuc.wheelgo.reportingsystem.Place;
import cz.nuc.wheelgo.reportingsystem.SpotsManager;

import java.io.IOException;
import java.util.Locale;

public class ReportPlace extends Activity {

    MyLocationManager lm;
    Location location = null;
    private Bitmap photo;
    private Integer id;
    private WheelGoApplication app = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportplace);

        // initialize location manager
        app = (WheelGoApplication) this.getApplication();

        Button odelat = (Button) this.findViewById(R.id.Send);
        odelat.getBackground().setColorFilter(
                new LightingColorFilter(0xFFCCDDFF, 0xFF00FF00));

        Spinner s = (Spinner) findViewById(R.id.comboCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void Report_onClick(View v) {
        EditText popis = (EditText) (this.findViewById(R.id.editDescribtion));

        location = lm.getLastKnownLocation();
        LatLng current_position = new LatLng(
                location.getLatitude(),
                location.getLongitude());

        Place newOne;
        if (id != null)
            newOne = new Place(id);
        else
            newOne = new Place();
        newOne.setCoordinates(current_position);
        newOne.setDescribtion(popis.getText().toString());
        newOne.setPhoto(this.photo);
        Spinner s = (Spinner) findViewById(R.id.comboCategory);

        SpotsManager sm = SpotsManager.getInstance();
        if (id != null)
            sm.editPlace(newOne);
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
        if (requestCode == 1) // TODO overit, jestli funguje ... melo by
            // vychazet z
            // startActivityForResult(cameraIntent, 1);
            if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, R.string.photo_not_taken,
                        Toast.LENGTH_SHORT);
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

        if (id != null) {
            // editting
            SpotsManager sm = SpotsManager.getInstance();
            Place place = sm.getDetailPlace(id);
            if (place == null)
                this.finish();

            location = new Location("GPS");
            float latitude = (float) (place.getCoordinates().latitude);
            float longitude = (float) (place.getCoordinates().longitude);

            location.setLatitude(latitude);
            location.setLongitude(longitude);

            TextView t = (TextView) findViewById(R.id.editDescribtion);
            t.setText(place.getDescription());

            Spinner s = (Spinner) findViewById(R.id.comboCategory);
        } else
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

    public class BackgroundAsyncTask extends AsyncTask<Place, String, Void> {

        @Override
        protected Void doInBackground(Place... params) {
            SpotsManager sm = SpotsManager.getInstance();
            sm.reportPlace(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            finish();


        }

    }
}