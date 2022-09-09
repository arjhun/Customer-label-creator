package com.arjenklaverstijn.android.customerlabelcreator.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.arjenklaverstijn.android.customerlabelcreator.R;
import com.arjenklaverstijn.android.customerlabelcreator.adapters.ProductRecyclerViewAdapter;
import com.arjenklaverstijn.android.customerlabelcreator.models.ProgressModel;
import com.arjenklaverstijn.android.customerlabelcreator.models.SaleModel;
import com.arjenklaverstijn.android.customerlabelcreator.databinding.FragmentProductListBinding;

import org.json.JSONArray;

import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class ProductListFragment extends Fragment {


    private static final Integer PAGENUMBER = 1;
    private FragmentProductListBinding binding;
    private SaleModel saleModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductListFragment newInstance(int columnCount) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentProductListBinding getBinding() {
        return binding;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ProgressModel progressModel = new ViewModelProvider(requireActivity()).get(ProgressModel.class);
        progressModel.increment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProductListBinding.inflate(inflater, container, false);

        RecyclerView view = (RecyclerView) binding.productList;
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        SaleModel saleModel = new ViewModelProvider(requireActivity()).get(SaleModel.class);

        ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(saleModel, requireActivity());
        recyclerView.setAdapter(adapter);

        saleModel.getProductList().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> products) {
                binding.nextButton2.setEnabled((products.size() != 0));
            }
        });

        binding.editTextAddProduct.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if( actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    Editable edTextChar = binding.editTextAddProduct.getText();
                    if(edTextChar.length() > 2) {
                        saleModel.addProduct(edTextChar.toString());
                        binding.editTextAddProduct.setText("");
                    }else{
                        binding.editTextAddProduct.setError("Please enter a least one product!");
                        binding.editTextAddProduct.requestFocus();
                    }
                }

                return false;
            }
        });

        binding.nextButton2.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_productListFragment3_to_PaymentFragment);
        });


        return binding.getRoot();
    }
}