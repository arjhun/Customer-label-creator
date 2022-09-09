package com.arjenklaverstijn.android.customerlabelcreator.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SaleModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<List<String>> productList;
    public String customerName;
    public String sportFriend;
    public String customerContact;
    public Date creationDate;
    public Date pickupDate;
    public boolean payedStatus;
    public String Notes;

    public SaleModel(){}

    public MutableLiveData<List<String>> getProductList() {
        if (productList == null) {
            productList = new MutableLiveData<List<String>>(new ArrayList<String>());
        }
        return productList;
    }

    public void addProduct(String string) {
        List<String> oldList = getProductList().getValue();
        oldList.add(string);
        List<String> newList = new ArrayList<String>(oldList);
        productList.setValue(newList);
    }

    public void removeProduct(int position) {
        List<String> oldList = getProductList().getValue();
        oldList.remove(position);
        List<String> newList = new ArrayList<String>(oldList);
        productList.setValue(newList);
    }
}
