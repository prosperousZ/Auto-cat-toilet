package com.example.autocattoilet.ui.waste;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.autocattoilet.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WasteFragment extends Fragment {

    private WasteViewModel wasteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wasteViewModel =
                ViewModelProviders.of(this).get(WasteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_waste, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        wasteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button button = (Button) root.findViewById(R.id.activate_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new HttpRequest().execute();

            }
        });

        return root;
    }

    public class HttpRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void...voids){

            byte[] result = null;
            String text = "";
            StringBuilder sb = new StringBuilder();

            try {

                URL url = new URL("http://192.168.0.23");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                text = "\\Command=forward";

                OutputStream os = conn.getOutputStream();
                BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                wr.write(url + text);
                wr.flush();
                wr.close();
                os.close();

                conn.connect();

//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String data;
//
//                while ((data = reader.readLine()) != null){
//
//                    sb.append(data + "\n");
//
//                }
//                reader.close();
//
//                return sb.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }






}
