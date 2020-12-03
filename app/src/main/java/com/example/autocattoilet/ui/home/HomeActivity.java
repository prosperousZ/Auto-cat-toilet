package com.example.autocattoilet.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Circular;
import com.anychart.anychart.CircularGauge;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.Fill;
import com.anychart.anychart.GaugePointersBar;
import com.anychart.anychart.SingleValueDataSet;
import com.anychart.anychart.SolidFill;
import com.anychart.anychart.TextHAlign;
import com.anychart.anychart.TextVAlign;
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

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try {
            loadIntoChart();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        downloadJSON("https://www.crowgotestact.com/test_service.php");
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

                try {
                    loadIntoChart();
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

    public void loadIntoChart() throws JSONException {
//        JSONArray jsonArray = new JSONArray(json);
//        double[] weightNum = new double[jsonArray.length()];
//        String[] weightDate = new String[jsonArray.length()];
        List<DataEntry> data = new ArrayList<>();

//        JSONObject obj = jsonArray.getJSONObject(0);
//        weightNum[0] = Double.parseDouble(obj.getString("weight"));
//        weightDate[0] = obj.getString("reading_time").substring(5, 10);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
//        = (findViewById(R.id.progress_bar));

        data.add(new ValueDataEntry("hi", 0d));

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(data);
        circularGauge.setFill("#fff")
                .setPadding(0d, 0d, 0d, 0d)
                .setMargin(100d, 100d, 100d, 100d);
        circularGauge.setStartAngle(0d);
        circularGauge.setSweepAngle(270d);

        Circular xAxis = circularGauge.getAxis()
                .setRadius(100d)
                .setWidth(1d)
                .setFill((Fill) null);
        xAxis.getScale()
                .setMinimum(0d)
                .setMaximum(100d);
        xAxis.setTicks("{ interval: 1 }")
                .setMinorTicks("{ interval: 1 }");
        xAxis.getLabels().setEnabled(false);
        xAxis.getTicks().setEnabled(false);
        xAxis.getMinorTicks().setEnabled(false);

        circularGauge.getLabel(0d)
                .setText("Temazepam, <span style=\"\">32%</span>")
                .setUseHtml(true)
                .setHAlign(TextHAlign.CENTER)
                .setVAlign(TextVAlign.MIDDLE);
        circularGauge.getLabel(0d)
                .setAnchor(EnumsAnchor.RIGHT_CENTER)
                .setPadding(0d, 10d, 0d, 0d)
                .setHeight(17d / 2d + "%")
                .setOffsetY(100d + "%")
                .setOffsetX(0d);

        GaugePointersBar bar0 = circularGauge.getBar(0d);
        bar0.setDataIndex(0d);
        bar0.setRadius(100d);
        bar0.setWidth(17d);
        bar0.setFill(new SolidFill("#64b5f6", 1d));
        bar0.setZIndex(5d);
        GaugePointersBar bar100 = circularGauge.getBar(100d);
        bar100.setDataIndex(5d);
        bar100.setRadius(100d);
        bar100.setWidth(17d);
        bar100.setFill(new SolidFill("#F5F4F4", 1d));
        bar100.setStroke("#e5e4e4", 1d, null, null, null);
        bar100.setZIndex(4d);

        anyChartView.setChart(circularGauge);
    }
}