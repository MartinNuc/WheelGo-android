package cz.nuc.wheelgo.androidclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import cz.nuc.wheelgo.androidclient.service.WheelGoService;
import cz.nuc.wheelgo.androidclient.service.dto.NavigationNode;
import cz.nuc.wheelgo.androidclient.service.dto.NavigationParameters;
import cz.nuc.wheelgo.androidclient.service.dto.ProblemDto;
import cz.nuc.wheelgo.androidclient.service.dto.Route;

/**
 * Created by mist on 14.11.13.
 */
public class WheelGoMapFragment extends MapFragment implements GoogleMap.OnCameraChangeListener, GoogleMap.OnMyLocationChangeListener,
        GoogleMap.OnMapLongClickListener, LocationListener, GoogleMap.OnInfoWindowClickListener {

    private final int PROBLEM_DETAIL_ACTIVITY_RESULT = 1;

    private ProgressDialog progressDialog;

    private double radius = 0.0009;

    private LocationManager locationManager;
    private String provider;
    private double userViewLatitude, userViewLongitude;
    private static double userPositionLatitude, userPositionLongitude;

    private static LatLng navigationTarget = null;

    private static LruCache<Long, ProblemDto> problems = new LruCache<Long, ProblemDto>(50);
    private static List<NavigationNode> route = new LinkedList<NavigationNode>();
    private static Set<ProblemDto> avoidedProblems = new HashSet<ProblemDto>();
    private static ProblemDto shownProblem = null;
    private static Map<Marker, ProblemDto> markers = new HashMap<Marker, ProblemDto>();

    AtomicBoolean loading = new AtomicBoolean(false);
    AtomicBoolean gpsFixed = new AtomicBoolean(false);

    public WheelGoMapFragment() {

    }

    public WheelGoMapFragment(boolean dontJumpOnCurrentLocation) {
        if (dontJumpOnCurrentLocation == true) {
            gpsFixed.set(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // Get the location manager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Toast.makeText(getActivity(), "Location not available.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getMap().setOnCameraChangeListener(this);
        getMap().setOnMyLocationChangeListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setMyLocationEnabled(true);
        getMap().setOnInfoWindowClickListener(this);

        if (shownProblem != null)
        {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(shownProblem.latitude, shownProblem.longitude)).zoom(18).build();
            getMap().moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            shownProblem = null;
            gpsFixed.set(true);
        }

        refreshProblems();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map_type:
                if (getMap().getMapType() == GoogleMap.MAP_TYPE_HYBRID)
                {
                    getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                else
                {
                    getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
                return false;
            case R.id.action_refresh:
                refreshProblems(true);
            default:
                break;
        }

        return false;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        if (loading.get() == false && (Math.abs(userViewLatitude - cameraPosition.target.latitude) > radius || Math.abs(userViewLongitude - cameraPosition.target.longitude) > radius)) {
            System.out.println("Refreshing problems");
            userViewLatitude = cameraPosition.target.latitude;
            userViewLongitude = cameraPosition.target.longitude;
            refreshProblems();
        }
    }

    private void refreshProblems() {
        refreshProblems(false);
    }

    private void refreshProblems(boolean clearMarks) {

        if (clearMarks == true)
        {
            problems.evictAll();
            redrawMap();
        }

        if (userViewLatitude == 0 || userViewLongitude == 0)
        {
            userViewLatitude = userPositionLatitude;
            userViewLongitude = userPositionLongitude;
        }

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getApplicationContext());
        String ip = prefs.getString("server_ip", null);
        loading.set(true);
        new LoadProblemsTask().execute(ip, userViewLatitude + "", userViewLongitude + "");
    }

    private void redrawMap() {
        getMap().clear();
        markers.clear();
        List<ProblemDto> navigationProblems = ((MainActivity) getActivity()).getNavigationProblems();
        for (ProblemDto p : problems.snapshot().values()) {
            if (navigationProblems.contains(p) == false && p != shownProblem) {
                LatLng position = new LatLng(p.latitude, p.longitude);
                Marker m = getMap().addMarker(new MarkerOptions()
                        .title(p.name)
                        .snippet("Klikněte sem pro detail")
                        .position(position));
                markers.put(m, p);

                boolean active = false;
                if (avoidedProblems.contains(p))
                {
                    active = true;
                }
                setMarkerIcon(m, p, active);
            }
        }

        for (ProblemDto p : navigationProblems) {
            if (p != shownProblem) {
                LatLng position = new LatLng(p.latitude, p.longitude);
                Marker m = getMap().addMarker(new MarkerOptions()
                        .title(p.name)
                        .snippet("Klikněte sem pro detail")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .position(position));
                markers.put(m, p);
                boolean active = false;
                if (avoidedProblems.contains(p))
                {
                    active = true;
                }
                setMarkerIcon(m, p, active);
            }
        }

        if (shownProblem != null) {
            LatLng position = new LatLng(shownProblem.latitude, shownProblem.longitude);
            Marker m = getMap().addMarker(new MarkerOptions()
                    .title(shownProblem.name)
                    .snippet("Klikněte sem pro detail")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(position));
            boolean active = false;
            if (avoidedProblems.contains(shownProblem))
            {
                active = true;
            }
            setMarkerIcon(m, shownProblem, active);
            markers.put(m, shownProblem);
            m.showInfoWindow();
        }

        PolylineOptions routePolygon = new PolylineOptions();
        for (NavigationNode n : route) {
            routePolygon.add(new LatLng(n.latitude, n.longitude));
        }
        routePolygon.color(Color.RED);
        getMap().addPolyline(routePolygon);

    }

    private void setMarkerIcon(Marker m, ProblemDto p, boolean active) {
        switch (p.category)
        {
            case STAVEBNI_PRACE:
                if (active)
                {
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.construction_active));
                }
                else
                {
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.construction));
                }
                break;
            case TECHNICKA_ZAVADA:
                if (active)
                {
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.technical_fail_active));
                }
                else
                {
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.technical_fail));
                }
                break;
            default:
                if (active)
                {
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.problem_active));
                }
                else
                {
                    m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.problem));
                }
                break;

        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (gpsFixed.get() == false && shownProblem == null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(location.getLatitude(), location.getLongitude())).zoom(18).build();

            getMap().moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            gpsFixed.set(true);
        }
    }

    private void findRoute()
    {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getApplicationContext());
        String ip = prefs.getString("server_ip", null);
        Integer maxIncline = Integer.parseInt(prefs.getString("max_incline", "30"));
        progressDialog = ProgressDialog.show(getActivity(), "", "Hledám trasu, čekejte...");
        progressDialog.setCancelable(true);
        new NavigationTask().execute(ip, userPositionLatitude + "", userPositionLongitude + "", navigationTarget.latitude + "", navigationTarget.longitude + "", maxIncline + "");
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        navigationTarget = latLng;
                        findRoute();
                        break;

                    case DialogInterface.BUTTON_NEUTRAL:
                        Intent intent = new Intent(getActivity(), AddProblemActivity.class);
                        intent.putExtra(AddProblemActivity.LATITUDE, latLng.latitude);
                        intent.putExtra(AddProblemActivity.LONGITUDE, latLng.longitude);
                        startActivity(intent);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Navigovat na toto místo?").setPositiveButton("Ano", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).setNeutralButton("Nahlásit zde problém", dialogClickListener).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (PROBLEM_DETAIL_ACTIVITY_RESULT):
                if (resultCode == Activity.RESULT_OK) {
                    Long problemToAvoidId = data.getLongExtra(ProblemDetailActivity.AVOID_PROBLEM, -1);
                    if (problemToAvoidId != -1)
                    {
                        ProblemDto p = new ProblemDto();
                        p.id = problemToAvoidId;
                        if (avoidedProblems.contains(p))
                        {
                            avoidedProblems.remove(p);
                        }
                        else
                        {
                            avoidedProblems.add(p);
                        }

                        redrawMap();

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        findRoute();
                                        break;
                                }
                            }
                        };

                        if (route != null && route.size() != 0)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Chcete trasu přeplánovat?").setPositiveButton("Ano", dialogClickListener)
                                    .setNegativeButton("Ještě ne", dialogClickListener).show();
                        }

                    }
                }
                break;
        }
        refreshProblems(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.userPositionLatitude = location.getLatitude();
        this.userPositionLongitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void showMarker(ProblemDto p) {
        this.shownProblem = p;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ProblemDto p = markers.get(marker);
        Intent intent = new Intent(getActivity(), ProblemDetailActivity.class);
        intent.putExtra(ProblemDetailActivity.PROBLEM_ID, p.id);
        startActivityForResult(intent, PROBLEM_DETAIL_ACTIVITY_RESULT);
        /*if (avoidedProblems.contains(p))
        {
            avoidedProblems.remove(p);
        }
        else
        {
            avoidedProblems.add(p);
        }

        redrawMap();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        findRoute();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Chcete trasu přeplánovat?").setPositiveButton("Ano", dialogClickListener)
                .setNegativeButton("Ještě ne", dialogClickListener).show();

        return;*/
    }

    private class LoadProblemsTask extends AsyncTask<String, Void, List<ProblemDto>> {

        @Override
        protected List<ProblemDto> doInBackground(String... params) {
            WheelGoService port = new WheelGoService(params[0]);
            double latitude = Double.parseDouble(params[1]);
            double longitude = Double.parseDouble(params[2]);
            double radius = 1;
            try {
                return port.getProblemsAround(latitude, longitude, radius);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ProblemDto> result) {
            loading.set(false);
            if (result == null) {
                Toast.makeText(getActivity(), "Nepodarilo se nacist problemy.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                for (ProblemDto p : result) {
                    problems.put(p.id, p);
                }
            }
            redrawMap();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    private class NavigationTask extends AsyncTask<String, Void, Route> {

        @Override
        protected Route doInBackground(String... params) {
            WheelGoService port = new WheelGoService(params[0]);
            double fromLatitude = Double.parseDouble(params[1]);
            double fromLongitude = Double.parseDouble(params[2]);
            double toLatitude = Double.parseDouble(params[3]);
            double toLongitude = Double.parseDouble(params[4]);
            Integer maxIncline = Integer.parseInt(params[5]);
            List<ProblemDto> problemsToAvoid = new LinkedList<ProblemDto>();
            for (ProblemDto p : WheelGoMapFragment.avoidedProblems)
            {
                problemsToAvoid.add(p);
            }
            NavigationParameters param = new NavigationParameters();
            param.latitudeFrom = fromLatitude;
            param.longitudeFrom = fromLongitude;
            param.latitudeTo = toLatitude;
            param.longitudeTo = toLongitude;
            param.problemsToAvoid = problemsToAvoid;
            param.maxIncline = maxIncline;
            try {
                return port.navigate(param);
            }
            catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Route result) {
            progressDialog.dismiss();
            if (result == null) {
                Toast.makeText(getActivity(), "Nepodarilo se naplanovat trasu.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                List<ProblemDto> navigationProblems = new LinkedList<ProblemDto>();
                for (ProblemDto p : result.problems) {
                    navigationProblems.add(p);
                }
                ((MainActivity) getActivity()).setNavigationProblems(navigationProblems);

                // add route
                route.clear();
                for (NavigationNode node : result.path) {
                    route.add(node);
                }

                redrawMap();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
