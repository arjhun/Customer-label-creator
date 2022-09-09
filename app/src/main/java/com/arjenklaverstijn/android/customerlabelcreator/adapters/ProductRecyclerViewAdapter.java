package com.arjenklaverstijn.android.customerlabelcreator.adapters;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.arjenklaverstijn.android.customerlabelcreator.databinding.FragmentProductBinding;
import com.arjenklaverstijn.android.customerlabelcreator.models.SaleModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link String}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    private SaleModel _model;
    private LifecycleOwner _owner;

    public ProductRecyclerViewAdapter(SaleModel model, LifecycleOwner owner) {

        this._model = model;
        _owner = owner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        _model.getProductList().observe(_owner, new Observer<List<String>>() {

            @Override
            public void onChanged(List<String> strings) {
                notifyDataSetChanged();
            }
        });
        return new ViewHolder(FragmentProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(_model.getProductList().getValue().get(position));
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "Deleted",
                        Toast.LENGTH_SHORT).show();

                _model.removeProduct(holder.getAbsoluteAdapterPosition());
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
            }
            
        });
    }

    @Override
    public int getItemCount() {
        return _model.getProductList().getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public ImageButton mDeleteButton;

        public ViewHolder(FragmentProductBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
            mDeleteButton = binding.imageButton;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}