package cz.nuc.wheelgo.androidclient;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference connectionPref = findPreference("server_ip");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        connectionPref.setSummary(sharedPref.getString("server_ip", ""));

        connectionPref = findPreference("max_incline");
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        connectionPref.setSummary(sharedPref.getString("max_incline", ""));

        EditTextPreference pref = (EditTextPreference)findPreference("max_incline");
        pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("server_ip")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
        if (key.equals("max_incline")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}