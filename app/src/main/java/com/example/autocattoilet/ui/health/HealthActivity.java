package com.example.autocattoilet.ui.health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HealthActivity extends AppCompatActivity {

//    private AppBarConfiguration mAppBarConfiguration;

   // ActionBar actionBar = getActionBar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        listView = findViewById(R.id.any_chart_view);
        downloadJSON("https://www.crowgotestact.com/test_service.php");

    }

    /*
     This function will ensure the back button will navigate to the previous page.
     */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {
                    loadIntoChart(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
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
    public void loadIntoChart(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        double[] weightNum = new double[jsonArray.length()];
        String[] weightDate = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            weightNum[i] = Double.parseDouble(obj.getString("weight"));
            weightDate[i] = obj.getString("reading_time").substring(0, 10);

        }
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry(weightDate[0], weightNum[0]));
        data.add(new ValueDataEntry(weightDate[1], weightNum[1]));
//        data.add(new ValueDataEntry(weightDate[2], weightNum[2]));
//        data.add(new ValueDataEntry(weightDate[3], weightNum[3]));
//        data.add(new ValueDataEntry(weightDate[4], weightNum[4]));
//        data.add(new ValueDataEntry(weightDate[5], weightNum[5]));
//        data.add(new ValueDataEntry(weightDate[6], weightNum[6]));

        CartesianSeriesColumn column = cartesian.column(data);

        column.getTooltip()
                .setTitleFormat("{%X}")
                .setPosition(Position.CENTER_BOTTOM)
                .setAnchor(EnumsAnchor.CENTER_BOTTOM)
                .setOffsetX(0d)
                .setOffsetY(5d)
                .setFormat("{%Value}{groupsSeparator: } lbs");


        cartesian.setAnimation(true);
        cartesian.setTitle("Cat weight between: " + weightDate[0] + " - " + weightDate[6]);

        cartesian.getYScale().setMinimum(0d);

        cartesian.getYAxis((double) 0).getLabels().setFormat("{%Value}{groupsSeparator: } lbs");

        cartesian.getTooltip().setPositionMode(TooltipPositionMode.POINT);
        cartesian.getInteractivity().setHoverMode(HoverMode.BY_X);

        cartesian.getXAxis((double) 0).setTitle("Date");
        cartesian.getYAxis((double) 0).setTitle("Weight");

        anyChartView.setChart(cartesian);
    }
}