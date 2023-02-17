package com.example.vaxcare.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.example.vaxcare.databinding.DialogAddAppointmentBinding;
import com.example.vaxcare.listeners.OnDialogActionListener;
import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.viewmodel.AppointmentDialogViewModel;

import java.util.List;
import java.util.Objects;

public class AddAppointmentDialog extends DialogFragment {
    private DialogAddAppointmentBinding binding;
    private OnDialogActionListener onDialogActionListener;

    public AddAppointmentDialog(OnDialogActionListener onDialogActionListener) {
        this.onDialogActionListener = onDialogActionListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_appointment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppointmentDialogViewModel viewModel = new ViewModelProvider(this).get(AppointmentDialogViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getVaccineList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Log.e("Check Error", String.valueOf(strings.size()));
                if (!strings.isEmpty()){
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, strings);
                    binding.vaccineListACTV.setAdapter(adapter);
                    binding.vaccineListACTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            viewModel.setVaccineName(strings.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
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
