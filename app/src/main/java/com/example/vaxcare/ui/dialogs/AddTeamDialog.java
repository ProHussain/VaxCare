package com.example.vaxcare.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.DialogAddTeamBinding;
import com.example.vaxcare.listeners.OnDialogActionListener;
import com.example.vaxcare.viewmodel.AddTeamViewModel;

import java.util.Objects;

public class AddTeamDialog extends DialogFragment {

    private DialogAddTeamBinding binding;
    private OnDialogActionListener onDialogActionListener;

    public AddTeamDialog(OnDialogActionListener onDialogActionListener) {
        this.onDialogActionListener = onDialogActionListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_team, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddTeamViewModel viewModel = new ViewModelProvider(this).get(AddTeamViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getIsAdded().observe(getViewLifecycleOwner(), isAdded -> {
            if (isAdded) {
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
