package cz.nuc.wheelgo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import com.google.android.maps.*;
import cz.nuc.wheelgo.reportingsystem.*;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMyLocationChangeListener, GoogleMap.OnCameraChangeListener {

    public GoogleMap mMap;

    AtomicBoolean loadingMarkers = new AtomicBoolean(false);

    private WheelGoApplication app;
    private boolean started=false;
    Map<String, Spot> spots = new HashMap<String, Spot>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        getWindow().getAttributes().format = android.graphics.PixelFormat.RGBA_8888;

        // initialize location manager
        app = (WheelGoApplication) this.getApplication();
        app.startGPS();

        setUpMapIfNeeded();
    }

    /*
     * move zoom to right/left side of the screen according to the settings
     */
    private void alignZoomButtons() {
        // move zoom to right/left side of the screen according to the settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        LinearLayout ll = (LinearLayout) findViewById(R.id.curtainZoom);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll.getLayoutParams();
        if (prefs.getBoolean("left_handed", false)) {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        }
        ll.setLayoutParams(params); //causes layout update
    }

    public List<Spot> loadMarkers(LatLng loc, int radius) {

        /**** Overlays for map objects ****/
        // places overlay
        SpotsManager sm = SpotsManager.getInstance(this);
        return sm.load(loc, radius);
    }

    public void addMarkersToMap(List<Spot> items) {

        Marker m;

        spots.clear();
        mMap.clear();
        if (!items.isEmpty()) {
            for (Spot item : items)
            {
                m = null;
                switch (item.getType())
                {
                    case TIP:
                        m = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(item.getCoordinates().latitude, item.getCoordinates().longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.tip)));
                        break;
                    case PROBLEM:
                        m = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(item.getCoordinates().latitude, item.getCoordinates().longitude))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.exclamation_mark)));
                        break;
                    case PLACE:
                        m = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(item.getCoordinates().latitude, item.getCoordinates().longitude))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.circle)));
                        break;
                }
                m.setDraggable(true);
                m.setTitle(item.getDescription());
                spots.put(m.getId(), item);
            }
        }
    }

    /**
     * Report problem button
     *
     * @param v
     */
    public void ReportProblemButton_onClick(View v) {
        Location l = mMap.getMyLocation();
        if (l==null)
            return;
        LatLng ll = new LatLng(l.getLatitude(), l.getLongitude());
        CameraUpdate cu = CameraUpdateFactory.newLatLng(ll);
        mMap.animateCamera(cu);
        startActivity(new Intent(getApplicationContext(), ReportProblem.class));
    }

    /**
     * Center position button
     *
     * @param v
     */
    public void CenterPositionButton_onClick(View v) {
        animateToMyLocation();
    }

    /**
     * Zoom in button
     *
     * @param v
     */
    public void ZoomIn_onClick(View v) {
        CameraUpdate cu = CameraUpdateFactory.zoomIn();
        mMap.animateCamera(cu);
    }

    /**
     * Zoom out button
     *
     * @param v
     */
    public void ZoomOut_onClick(View v) {
        CameraUpdate cu = CameraUpdateFactory.zoomOut();
        mMap.animateCamera(cu);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Spot clicked = spots.get(marker.getId());
        Intent i;
        switch (clicked.getType())
        {
            case PLACE:
                i = new Intent(this, DetailPlace.class);
                i.putExtra(DetailPlace.DETAIL, clicked.getId());
                startActivity(i);
                break;
            case TIP:
                i = new Intent(this, DetailTip.class);
                i.putExtra(DetailTip.DETAIL, clicked.getId());
                startActivity(i);
                break;
            case PROBLEM:
                i = new Intent(this, DetailProblem.class);
                i.putExtra(DetailProblem.DETAIL, clicked.getId());
                startActivity(i);
                break;
        }
    }

    /************** MENU **************/
    /**
     * Creating menu from xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mapmenu, menu);
        return true;
    }

    private boolean animateToMyLocation()
    {
        Location l;
        l = mMap.getMyLocation();
        return animateToMyLocation(l);
    }

    private boolean animateToMyLocation(Location l)
    {
        if (l == null)
            return false;
        LatLng ll;
        CameraUpdate cu;
        ll = new LatLng(l.getLatitude(), l.getLongitude());
        cu = CameraUpdateFactory.newLatLngZoom(ll, 14);
        mMap.animateCamera(cu);
        return true;
    }

    /**
     * Menu selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        GeoPoint gp;
        switch (item.getItemId()) {
            case R.id.reportPlace:
                // report place
                if (animateToMyLocation()==true)
                    startActivity(new Intent(this, ReportPlace.class));
                else
                    Toast.makeText(this, "Nelze urcit polohu. Neni mozne vytvorit hlaseni", Toast.LENGTH_LONG).show();
                return true;
            case R.id.reportTip:
                // report tip
                if (animateToMyLocation()==true)
                    startActivity(new Intent(this, ReportTip.class));
                else
                    Toast.makeText(this, "Nelze urcit polohu. Neni mozne vytvorit hlaseni", Toast.LENGTH_LONG).show();
                return true;
            case R.id.mnuMapSatellite:
                // switch between sattelite view and normal view
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.mnuSettings:
                startActivity(new Intent(this, Settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * When activity is activated again
     */
    @Override
    public void onResume() {
        alignZoomButtons();
        setUpMapIfNeeded();
        new LoadMarkersTask().execute(mMap.getCameraPosition().target);
        super.onResume();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnCameraChangeListener(this);
        mMap.setOnMyLocationChangeListener(this);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent streetView = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.streetview:cbll=" + latLng.latitude + "," + latLng.longitude + "&cbp=1,99.56,,1,-5.27&mz=21"));
                startActivity(streetView);
            }
        });
        mMap.setOnCameraChangeListener(this);
    }

    /**
     * Since this is our main activity we have to take care about location
     * manager
     */
    @Override
    public void onStart() {
        app.startGPS();
        super.onStart();
    }

    /**
     * Since this is our main activity we have to take care about location
     * manager
     */
    @Override
    public void onPause() {
		/*myLocationOverlay.disableCompass();
		myLocationOverlay.disableMyLocation();    */
        app.stopGPS();
        super.onStop();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition)
    {
        new LoadMarkersTask().execute(cameraPosition.target);
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (this.started == false)
        {
            animateToMyLocation(location);
            this.started = true;
        }
    }

    class LoadMarkersTask extends AsyncTask<LatLng, Void, List<Spot>> {

        private Exception exception;

        @Override
        protected List<Spot> doInBackground(LatLng... params) {
            try {
                return loadMarkers(params[0], 500);
            } catch (Exception e) {
                this.exception = e;
                return new ArrayList<Spot>();
            }
        }

        @Override
        protected void onPreExecute()
        {
            // if we are already loading markers
            if (loadingMarkers.get() == true)
            {
                this.cancel(true);
            }
            loadingMarkers.set(true);
        }

        @Override
        protected void onPostExecute(List<Spot> result) {
            if (result.isEmpty())
                Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
            addMarkersToMap(result);
            loadingMarkers.set(false);

        }
    }
}
