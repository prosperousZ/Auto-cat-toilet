package com.example.autocattoilet.ui.health;

import android.content.Intent;
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
    This class will create a fragment that will lead to the HealthActivity class.
 */
public class HealthFragment extends Fragment {

    private HealthViewModel galleryViewModel;

    /*
        This method will display text and create a button that leads to the HealthActivity class.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel = ViewModelProviders.of(this).get(HealthViewModel.class);
        View root = inflater.inflate(R.layout.fragment_health, container, false);

        Button button = (Button) root.findViewById(R.id.weight_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHealthActivity();
            }
        });

        return root;
    }

    /*
        This method will open the HealthActivity class.
     */
    public void openHealthActivity(){

        Intent intent = new Intent(getActivity(), HealthActivity.class);
        startActivity(intent);

    }

}
