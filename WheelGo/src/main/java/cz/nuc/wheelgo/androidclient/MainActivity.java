package cz.nuc.wheelgo.androidclient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

import cz.nuc.wheelgo.androidclient.service.dto.ProblemDto;

;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private List<ProblemDto> navigationProblems = new LinkedList<ProblemDto>();
    private List<ProblemDto> navigationProblemsToAvoid = new LinkedList<ProblemDto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void setNavigationProblems(List<ProblemDto> problems) {
        navigationProblems = problems;
    }

    public List<ProblemDto> getNavigationProblems() {
        return navigationProblems;
    }

    public List<ProblemDto> getNavigationProblemsToAvoid() {
        return navigationProblemsToAvoid;
    }

    public void showProblemOnMap(ProblemDto problem) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        WheelGoMapFragment frag = new WheelGoMapFragment();
        ft.replace(R.id.container, frag);
        ft.commit();
        frag.showMarker(problem);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_problems_to_avoid);
                break;
            case 3:
                mTitle = getString(R.string.title_navigation_problems);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void centerPositionButton_onClick(View v)
    {
        WheelGoMapFragment fragment = (WheelGoMapFragment) (getFragmentManager().findFragmentById(R.id.googlemap));
        if (fragment != null) {
            fragment.centerPositionButton_onClick(v);
        }
    }

    public void zoomIn_onClick(View v)
    {
        WheelGoMapFragment fragment = (WheelGoMapFragment) (getFragmentManager().findFragmentById(R.id.googlemap));
        if (fragment != null) {
            fragment.zoomIn_onClick(v);
        }
    }

    public void zoomOut_onClick(View v)
    {
        WheelGoMapFragment fragment = (WheelGoMapFragment) (getFragmentManager().findFragmentById(R.id.googlemap));
        if (fragment != null) {
            fragment.zoomOut_onClick(v);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Fragment fragment = (getFragmentManager().findFragmentById(R.id.googlemap));
            if (fragment != null) {
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.remove(fragment);
                ft.commit();
            }
            fragment = (getFragmentManager().findFragmentById(R.id.problems_fragment));
            if (fragment != null) {
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.remove(fragment);
                ft.commit();
            }
            fragment = (getFragmentManager().findFragmentById(R.id.avoid_problems_fragment));
            if (fragment != null) {
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.remove(fragment);
                ft.commit();
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Integer tabIndex = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView;

            switch (tabIndex) {
                case 1:
                    rootView = inflater.inflate(R.layout.map, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_navigation_setup, container, false);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_navigation_overview, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(tabIndex.toString());
                    break;
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
