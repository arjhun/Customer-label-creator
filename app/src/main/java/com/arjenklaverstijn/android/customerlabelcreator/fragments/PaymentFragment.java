package com.arjenklaverstijn.android.customerlabelcreator.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arjenklaverstijn.android.customerlabelcreator.models.ProgressModel;
import com.arjenklaverstijn.android.customerlabelcreator.databinding.FragmentPaymentBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final Integer PAGENUMBER = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentPaymentBinding binding;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.;
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        final ProgressModel progressModel = new ViewModelProvider(requireActivity()).get(ProgressModel.class);
        progressModel.increment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen;
        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent intent = result.getData();
                        Bundle extras = intent.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        binding.imageView.setImageBitmap(imageBitmap);
                        binding.payedStatusCb.setEnabled(true);
                    }
                });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraResultLauncher.launch(takePictureIntent);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}