package com.example.autocattoilet.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.autocattoilet.R;

/*
    This class will create the home page fragment.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    /*
        This method will create a button that will link to the a webpage to turn on the motor.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button button = (Button) root.findViewById(R.id.activate_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri webpage = Uri.parse("http://10.0.1.11/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(webIntent);
            }
        });

        return root;
    }

}
