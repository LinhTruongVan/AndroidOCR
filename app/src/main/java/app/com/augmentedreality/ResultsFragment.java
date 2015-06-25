package app.com.augmentedreality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ResultsFragment extends Fragment {
    private ListView listImagePaths;
    private Button btnLoadListImagePaths;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.results_tab, container, false);

        listImagePaths = (ListView) rootView.findViewById(R.id.listImagePaths);
        btnLoadListImagePaths = (Button) rootView.findViewById(R.id.btnLoadListImagePaths);
        btnLoadListImagePaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CameraActivity.imagePaths.size() > 0) {
                    // Define a new Adapter
                    // First parameter - Context
                    // Second parameter - Layout for the row
                    // Third parameter - ID of the TextView to which the data is written
                    // Forth - the Array of data
                    arrayAdapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.simple_row, R.id.txtRow, CameraActivity.imagePaths);

                    // Assign arrayAdapter to listImagePaths
                    listImagePaths.setAdapter(arrayAdapter);
                }else{
                    Toast.makeText(getActivity(),
                            "List of imagePaths is empty!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        listImagePaths.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        (String) listImagePaths.getItemAtPosition(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }


}