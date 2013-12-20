package cz.nuc.wheelgo.androidclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesClient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.nuc.wheelgo.androidclient.service.WheelGoService;
import cz.nuc.wheelgo.androidclient.service.dto.Category;
import cz.nuc.wheelgo.androidclient.service.dto.ProblemDto;

/**
 * Created by mist on 14.11.13.
 */
public class AddProblemActivity extends Activity {

    final public static String LATITUDE = "latitude";
    final public static String LONGITUDE = "longitude";

    public static final int TAKE_PICTURE = 1;

    private MyLocation myLocation;

    private ProgressDialog progressDialog;

    private Double latitude, longitude;
    private byte[] image;
    private LocationManager locationManager;
    private String provider;
    private boolean locationManuallySet = false;
    private Date expiration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<Category> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Category.values());
        Spinner spinner = (Spinner) findViewById(R.id.category);
        spinner.setAdapter(adapter);

        Spinner expiration = (Spinner) findViewById(R.id.expiration);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.expiration, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expiration.setAdapter(adapter2);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // get coordinations from Intent
        Intent intent = getIntent();
        if (intent.hasExtra(LATITUDE))
        {
            // set manually
            latitude = intent.getDoubleExtra(LATITUDE, 0);
            longitude = intent.getDoubleExtra(LONGITUDE, 0);
            locationManuallySet = true;
        }
        else
        {
            MyLocation.LocationResult locationResult;
            locationResult = new MyLocation.LocationResult(){
                @Override
                public void gotLocation(Location location){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            };
            myLocation = new MyLocation();
            myLocation.getLocation(this, locationResult);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (myLocation != null)
        {
            myLocation.cancelTimer();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_add_problem, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (TAKE_PICTURE):
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap mImageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                    image = stream.toByteArray();

                    ImageView photo = (ImageView) findViewById(R.id.photo);
                    photo.setImageBitmap(mImageBitmap);
                }
                break;
        }
    }

    public static Date getExpirationDateFromSpinner(int position)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        switch (position)
        {
            case 0:
                c.add(Calendar.HOUR, 3);
                break;
            case 1:
                c.add(Calendar.HOUR, 6);
                break;
            case 2:
                c.add(Calendar.HOUR, 12);
                break;
            case 3:
                c.add(Calendar.HOUR, 24*7);
                break;
            case 4:
                c.add(Calendar.HOUR, 24*7*2);
                break;
            case 5:
                c.add(Calendar.HOUR, 24*7*4);
                break;
            case 6:
                return null;
        }

        return c.getTime();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.confirm:

                if (latitude == null || longitude == null)
                {
                    Toast.makeText(this, "Lokace není dostupná.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                progressDialog = ProgressDialog.show(this, "", "Odesílá se hlášení, čekejte...");
                progressDialog.setCancelable(true);
                // send to server
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                String ip = prefs.getString("server_ip", null);
                TextView name = (TextView) findViewById(R.id.txtName);
                Spinner spinner = (Spinner) findViewById(R.id.category);
                Category category = ((Category)spinner.getSelectedItem());

                spinner = (Spinner) findViewById(R.id.expiration);
                expiration = getExpirationDateFromSpinner(spinner.getSelectedItemPosition());

                new AddProblemTask().execute(ip, ""+latitude, ""+longitude, name.getText().toString(), category.ordinal()+"");
                return true;
            case R.id.take_photo:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AddProblemTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            WheelGoService port = new WheelGoService(params[0]);
            ProblemDto problem = new ProblemDto();
            problem.latitude = Double.parseDouble(params[1]);
            problem.longitude = Double.parseDouble(params[2]);
            problem.name = params[3];
            problem.category = Category.values()[Integer.parseInt(params[4])];
            if (image != null)
            {
                problem.imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
            }
            if (expiration != null)
            {
                problem.expirationTimestamp = expiration.getTime();
            }
            else
            {
                problem.expirationTimestamp = null;
            }
            try {
                return port.addProblem(problem);
            }
            catch (Exception e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result == true)
            {
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Hlášení se nepodarilo odeslat.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
