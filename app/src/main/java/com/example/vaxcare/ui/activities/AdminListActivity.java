package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.vaxcare.R;
import com.example.vaxcare.adapter.AdminListAdapter;
import com.example.vaxcare.databinding.ActivityAdminListBinding;
import com.example.vaxcare.listeners.OnDialogActionListener;
import com.example.vaxcare.ui.dialogs.AddAppointmentDialog;
import com.example.vaxcare.ui.dialogs.AddTeamDialog;
import com.example.vaxcare.ui.dialogs.AddVaccineDialog;
import com.example.vaxcare.viewmodel.AdminListViewModel;

public class AdminListActivity extends AppCompatActivity implements OnDialogActionListener {
    ActivityAdminListBinding binding;
    AdminListViewModel viewModel;
    AddVaccineDialog addVaccineDialog;
    AddTeamDialog addTeamDialog;
    String listType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_list);
        viewModel = new ViewModelProvider(this).get(AdminListViewModel.class);
        listType = getIntent().getStringExtra("listName");
        if (listType.equals("users") || listType.equals("request") || listType.equals("complete")) {
            binding.floatingActionButton.setVisibility(android.view.View.GONE);
        }
        viewModel.setListName(listType);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.adminListRV.setAdapter(new AdminListAdapter(listType));

        viewModel.getAdminList().observe(this, adminLists -> {
            if (adminLists != null) {
                AdminListAdapter adapter = (AdminListAdapter) binding.adminListRV.getAdapter();
                assert adapter != null;
                adapter.setAdminList(adminLists);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getAddData().observe(this, aBoolean -> {
            if (aBoolean) {
                if (listType.equals("vaccine")) {
                    showAddVaccineDialog();
                } else if (listType.equals("worker")) {
                    showAddWorkerDialog();
                }
                viewModel.setAddData(false);
            }
        });
    }

    private void showAddWorkerDialog() {
        addTeamDialog = new AddTeamDialog(AdminListActivity.this);
        addTeamDialog.show(getSupportFragmentManager(), "AddTeamDialog");
    }

    private void showAddVaccineDialog() {
        addVaccineDialog = new AddVaccineDialog(AdminListActivity.this);
        addVaccineDialog.show(getSupportFragmentManager(), "AddVaccineDialog");
    }

    @Override
    public void onClick() {
        if (listType.equals("vaccine")) {
            addVaccineDialog.dismiss();
        } else if (listType.equals("worker")) {
            addTeamDialog.dismiss();
        }
        viewModel.fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchData();
    }
}