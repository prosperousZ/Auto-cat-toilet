package com.example.autocattoilet.ui.waste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.example.autocattoilet.MainActivity;
import com.example.autocattoilet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WasteActivity extends AppCompatActivity {

    public double cycleCount;
    public int prevJsonLength;
    public HalfGauge halfGauge;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        cycleCount = 0;
        prevJsonLength = 0;

        downloadJSON();

        halfGauge = findViewById(R.id.halfGauge);
        Button button = findViewById(R.id.empty_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cycleCount = 0;
                halfGauge.setValue(cycleCount);
                editor.putString("cycle_count", "0");
                editor.putInt("prev_json_length", prevJsonLength);
                editor.apply();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    /*
        This will download the json file from the webpage.
     */
    private void downloadJSON() {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoGauge(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://www.crowgotestact.com/test_service.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    /*
        This will load the amount of cycles into a gauge.
     */
    public void loadIntoGauge(String json) throws JSONException {
        final JSONArray jsonArray = new JSONArray(json);

        String cycleStr = sharedPref.getString("cycle_count", Double.toString(cycleCount));
        cycleCount = Double.parseDouble(cycleStr);

        prevJsonLength = sharedPref.getInt("prev_json_length", prevJsonLength);

        int jsonArrayLength = jsonArray.length();

        // Previous length should never be greater.
        if(prevJsonLength > jsonArrayLength)
        {
            prevJsonLength = jsonArray.length();
        }

        // Update the cycles and previous length.
        while(prevJsonLength < jsonArrayLength)
        {
            cycleCount++;
            prevJsonLength++;
        }

        Range range = new Range();
        range.setColor(Color.parseColor("#00b20b"));
        range.setFrom(0.0);
        range.setTo(5.0);

        Range range2 = new Range();
        range2.setColor(Color.parseColor("#E3E500"));
        range2.setFrom(5.0);
        range2.setTo(10.0);

        Range range3 = new Range();
        range3.setColor(Color.parseColor("#ce0000"));
        range3.setFrom(10.0);
        range3.setTo(15.0);

        halfGauge.addRange(range);
        halfGauge.addRange(range2);
        halfGauge.addRange(range3);

        halfGauge.setMinValue(0.0);
        halfGauge.setMaxValue(15.0);
        halfGauge.setValue(cycleCount);
    }
}