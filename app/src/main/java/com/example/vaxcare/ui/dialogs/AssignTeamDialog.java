package com.example.vaxcare.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.DialogAssignTeamBinding;
import com.example.vaxcare.listeners.OnDialogActionListener;
import com.example.vaxcare.model.Responses.ApiResponse;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.viewmodel.AssignTeamViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssignTeamDialog extends DialogFragment {
    private DialogAssignTeamBinding binding;
    Appointment appointment;
    private OnDialogActionListener onDialogActionListener;

    public AssignTeamDialog(OnDialogActionListener onDialogActionListener, Appointment appointment) {
        this.onDialogActionListener = onDialogActionListener;
        this.appointment = appointment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_assign_team, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AssignTeamViewModel viewModel = new ViewModelProvider(this).get(AssignTeamViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getTeamList().observe(getViewLifecycleOwner(), teamList -> {
            if (!teamList.isEmpty()) {
                List<String> strings = new ArrayList<>();
                for (int i = 0; i < teamList.size(); i++) {
                    strings.add(teamList.get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, strings);
                binding.teamListSpinner.setAdapter(adapter);
                binding.teamListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        appointment.setTeam(teamList.get(position).getId());
                        viewModel.setAppointment(appointment);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });

        viewModel.getApiResponse().observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                onDialogActionListener.onClick();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        params.width = (int) (screenWidth * 0.9);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setAttributes(params);
    }
}
