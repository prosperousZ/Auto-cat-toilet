package com.example.autocattoilet.ui.waste;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.autocattoilet.R;

/*
    This class will create a fragment that will lead to the WasteActivity class through a button.
 */
public class WasteFragment extends Fragment
{
    private WasteViewModel wasteViewModel;

    /*
        This method will create the button to the WasteActivity class.
     */
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

    /*
        This class will open the WasteActivity class.
     */
    public void openWasteActivity(){

        Intent intent = new Intent(getActivity(), WasteActivity.class);
        startActivity(intent);

    }

}
