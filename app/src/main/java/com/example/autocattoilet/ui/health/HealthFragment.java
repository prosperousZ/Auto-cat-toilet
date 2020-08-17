package com.example.autocattoilet.ui.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.autocattoilet.R;

public class HealthFragment extends Fragment {

    private HealthViewModel galleryViewModel;
    ListView testList;

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



//        String[] values = new String[] {"Message1", "Message2", "Message3"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
//        testList.setAdapter(adapter);
        return root;
    }

    public void openHealthActivity(){

        Intent intent = new Intent(getActivity(), HealthActivity.class);
        startActivity(intent);

    }

}
