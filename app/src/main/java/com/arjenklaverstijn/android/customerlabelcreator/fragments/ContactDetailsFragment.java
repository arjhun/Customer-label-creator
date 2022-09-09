package com.arjenklaverstijn.android.customerlabelcreator.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arjenklaverstijn.android.customerlabelcreator.R;
import com.arjenklaverstijn.android.customerlabelcreator.databinding.FragmentContactDetailsBinding;
import java.util.Calendar;
import java.util.Date;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ContactDetailsFragment extends Fragment {

    private FragmentContactDetailsBinding binding;
    private SharedPreferences sharedPref;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(!sharedPref.contains("sportfriend_name")){
            Navigation.findNavController(getView()).navigate(R.id.sportFriendFragment);
        }else{
            binding.textViewSportfriend.setText(sharedPref.getString("sportfriend_name","Sportfriend"));
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {



        binding = FragmentContactDetailsBinding.inflate(inflater, container, false);

        binding.pickupTimeField.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(v.getContext(), (view, hourOfDay, minute) -> {
                    Date date = new Date();
                    String localizedDate = DateUtils.formatDateTime(v.getContext(), date.getTime(), DateUtils.FORMAT_SHOW_TIME);
                    binding.pickupTimeField.setText(localizedDate);
                }, mHour, mMinute, DateFormat.is24HourFormat(getActivity()));
                tpd.show();
            }
        });

        binding.pickupDateField.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(v.getContext());

                    datePicker.show();
            datePicker.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                Date date = new Date();
                String localizedDate = DateUtils.formatDateTime(v.getContext(), date.getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
                binding.pickupDateField.setText(localizedDate);
            });
        });

        binding.pickupDateField.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus && binding.pickupDateField.getText().length() == 0){
                binding.pickupDateField.performClick();
            }
        });

        binding.nextButton.setOnClickListener(v -> {
            if(formisValidated()) {
                Navigation.findNavController(v).navigate(R.id.action_ContactFragment_to_productListFragment3);
            }
        });


        return binding.getRoot();

    }

    private boolean formisValidated() {
        boolean hasError = false;
        if(binding.nameField.getText().length() < 2){
            binding.nameField.setError("Enter a readable name!");
            hasError = true;
        }else if(binding.contactDetailsField.getText().length() < 2){
            binding.contactDetailsField.setError("Enter contact info!");
            hasError = true;
        }else if(binding.pickupDateField.getText().length() < 2){
            binding.pickupDateField.setError("Enter a date!");
            hasError = true;
        }else if(binding.pickupTimeField.getText().length() < 2){
            binding.pickupTimeField.setError("Enter the time!");
            hasError = true;
        }
        return !hasError;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}

