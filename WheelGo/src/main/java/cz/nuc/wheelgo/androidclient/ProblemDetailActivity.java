package cz.nuc.wheelgo.androidclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.nuc.wheelgo.androidclient.service.WheelGoService;
import cz.nuc.wheelgo.androidclient.service.dto.ProblemDto;

/**
 * Created by mist on 20.12.13.
 */
public class ProblemDetailActivity extends Activity {

    public static final String PROBLEM = "problemid";
    public static final String AVOID_PROBLEM = "avoidproblemid";
    private ProblemDto problem;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_detail);
        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        problem = (ProblemDto) intent.getSerializableExtra(ProblemDetailActivity.PROBLEM);

        if (problem == null)
        {
            Toast.makeText(this, "Wrong problem id.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            String ip = prefs.getString("server_ip", null);
            progressDialog = ProgressDialog.show(this, "", "Načítam detaily, čekejte...");
            progressDialog.setCancelable(true);
            new LoadProblemDetailTask().execute(ip, problem.id + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_problem_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.delete:
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(this.getApplicationContext());
                String ip = prefs.getString("server_ip", null);
                progressDialog = ProgressDialog.show(this, "", "Mažu hlášení, čekejte...");
                progressDialog.setCancelable(true);
                new DeleteProblemTask().execute(ip, problem.id + "");

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void avoidProblemButtonClick(View v)
    {
        Intent resultIntent = new Intent();
        problem.imageBase64 = null;
        resultIntent.putExtra(AVOID_PROBLEM, problem);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public Activity getActivity()
    {
        return this;
    }

    private class LoadProblemDetailTask extends AsyncTask<String, Void, ProblemDto> {

        @Override
        protected ProblemDto doInBackground(String... params) {
            WheelGoService port = new WheelGoService(params[0]);
            Long problemId = Long.parseLong(params[1]);
            try {
                return port.getProblemDetail(problemId);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ProblemDto result) {
            progressDialog.dismiss();
            if (result == null) {
                Toast.makeText(getActivity(), "Nepodařilo se načíst detaily problému.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            } else {
                TextView category = (TextView) findViewById(R.id.category);
                category.setText(result.category.toString());

                TextView description = (TextView) findViewById(R.id.description);
                description.setText(result.name);

                TextView expiration = (TextView) findViewById(R.id.expiration);
                if (result.expirationTimestamp != null)
                {
                    Date expirationDate = new Date(result.expirationTimestamp);
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
                    expiration.setText("Vyprší: " + format.format(expirationDate));
                }
                else
                {
                    expiration.setText("");
                }

                if (result.imageBase64 != null)
                {
                    ImageView photo = (ImageView) findViewById(R.id.photo);
                    byte[] bytes = Base64.decode(result.imageBase64, Base64.DEFAULT);
                    photo.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class DeleteProblemTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            WheelGoService port = new WheelGoService(params[0]);
            Long problemId = Long.parseLong(params[1]);
            try {
                return port.deleteProblem(problemId);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result == null) {
                Toast.makeText(getActivity(), "Nepodařilo se hlášení vymazat.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            } else {
                finish();
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
