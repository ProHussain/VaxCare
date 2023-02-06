package com.example.vaxcare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vaxcare.R;
import com.example.vaxcare.activities.adapters.VaccinationListAdapter;
import com.example.vaxcare.activities.adapters.VaccinationTypeAdapter;
import com.example.vaxcare.models.VaccinationListModel;
import com.example.vaxcare.models.VaccinationTypeModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView horizontalRV;
    RecyclerView verticalRV;
    VaccinationTypeAdapter adapter;
    VaccinationListAdapter adapter1;
    private List<VaccinationTypeModel> vaccines;
    private List<VaccinationListModel> vaccinesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        horizontalRV=findViewById(R.id.horizontalRecyclerView);
        verticalRV=findViewById(R.id.verticalRecyclerView);
        vaccines=new ArrayList<>();
        vaccines.add(new VaccinationTypeModel( R.drawable.avfour,"corona"));
        vaccines.add(new VaccinationTypeModel( R.drawable.avfour,"polio"));
        vaccines.add(new VaccinationTypeModel( R.drawable.avfour,"abc"));
        vaccines.add(new VaccinationTypeModel( R.drawable.avfour,"abc"));
        vaccines.add(new VaccinationTypeModel( R.drawable.avfour,"abc"));
        adapter=new VaccinationTypeAdapter(vaccines);
        horizontalRV.setAdapter(adapter);
        vaccinesList=new ArrayList<>();
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        vaccinesList.add(new VaccinationListModel(R.drawable.ic_launcher_background,"altaf"));
        adapter1=new VaccinationListAdapter(vaccinesList);
        verticalRV.setAdapter(adapter1);

    }
}