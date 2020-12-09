package com.example.autocattoilet.ui.health;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CartesianSeriesColumn;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.Position;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;
import com.example.autocattoilet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
    This class will contain a column chart that displays the cat's weight overtime.
 */
public class HealthActivity extends AppCompatActivity {

    /*
        This method will begin to download the json file from the database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        downloadJSON();
    }

    /*
     This function will ensure the back button will navigate to the previous page.
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /*
        This will download the json file from the webpage.
     */
    private void downloadJSON() {

        /*
            This class will download a json file from the database.
         */
        class DownloadJSON extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            /*
                This method will execute when this activity is called.
             */
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoChart(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /*
                This method will download the json file from the database containing the cat data in the background.
             */
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
        This will load the json string into a column chart form.
     */
    public void loadIntoChart(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        double[] weightNum = new double[jsonArray.length()];
        String[] weightDate = new String[jsonArray.length()];
        List<DataEntry> data = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            weightNum[i] = Double.parseDouble(obj.getString("weight"));
            weightDate[i] = obj.getString("reading_time").substring(5, 10);

            // Add the data to the list
            if (weightNum[i] != 0.0)
                data.add(new ValueDataEntry(weightDate[i], weightNum[i]));
        }

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Cartesian cartesian = AnyChart.column();

        CartesianSeriesColumn column = cartesian.column(data);

        column.getTooltip()
                .setTitleFormat("{%X}")
                .setPosition(Position.CENTER_BOTTOM)
                .setAnchor(EnumsAnchor.CENTER_BOTTOM)
                .setOffsetX(0d)
                .setOffsetY(5d)
                .setFormat("{%Value}{groupsSeparator: } lbs");

        JSONObject objFirst = jsonArray.getJSONObject(0);
        JSONObject objLast = jsonArray.getJSONObject(jsonArray.length() - 1);

        cartesian.setAnimation(true);
        cartesian.setTitle("Cat weight between: " + objFirst.getString("reading_time").substring(0, 10) + " - " +
                objLast.getString("reading_time").substring(0, 10));

        cartesian.getYScale().setMinimum(0d);

        cartesian.getYAxis((double) 0).getLabels().setFormat("{%Value}{groupsSeparator: } lbs");

        cartesian.getTooltip().setPositionMode(TooltipPositionMode.POINT);
        cartesian.getInteractivity().setHoverMode(HoverMode.BY_X);

        cartesian.getXAxis((double) 0).setTitle("Date");
        cartesian.getYAxis((double) 0).setTitle("Weight");

        anyChartView.setChart(cartesian);

    }
}