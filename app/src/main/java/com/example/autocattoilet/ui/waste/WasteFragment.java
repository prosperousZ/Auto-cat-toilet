package com.example.autocattoilet.ui.waste;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.autocattoilet.R;
import com.example.autocattoilet.ui.health.HealthActivity;
import com.example.autocattoilet.ui.health.HealthViewModel;

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

public class WasteFragment extends Fragment
{

    private WasteViewModel wasteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        wasteViewModel = ViewModelProviders.of(this).get(WasteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_waste, container, false);

        Button button = (Button) root.findViewById(R.id.check_waste);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWasteActivity();
            }
        });

        return root;
    }

    public void openWasteActivity(){

        Intent intent = new Intent(getActivity(), WasteActivity.class);
        startActivity(intent);

    }

}
