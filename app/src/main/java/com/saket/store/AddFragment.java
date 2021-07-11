package com.saket.store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddFragment extends Fragment {

    private TextView shopName,Address,latitude,longitude,itemsEditText,offersEditText;

    private Button add;

    final DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items");
    final DatabaseReference databaseStores=FirebaseDatabase.getInstance().getReference("stores");
    private String storeId;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopName=view.findViewById(R.id.et_shopname);
        Address=view.findViewById(R.id.et_address_signup);
        latitude=view.findViewById(R.id.et_latitude);
        longitude=view.findViewById(R.id.et_longitude);
        itemsEditText=view.findViewById(R.id.et_items);
        offersEditText=view.findViewById(R.id.et_offers);
        navController= Navigation.findNavController(view);

        view.findViewById(R.id.submit).setOnClickListener(v->
        {
            saveData();
        });


    }
    public void saveData()
    {
        Gson gson = new Gson();
        String temp = itemsEditText.getText().toString().toUpperCase();
        String ar[]=temp.split(",");
        List<String> itemsList = new ArrayList<>();
        itemsList= Arrays.asList(ar);
        String items = gson.toJson(itemsList);
        String arr[]= offersEditText.getText().toString().split(",");
        List<String> offersList = new ArrayList<>();
        offersList = Arrays.asList(arr);
        String offers = gson.toJson(offersList);
        UUID uuid = UUID.randomUUID();
        String storeId = uuid.toString().replace("-", "");
        storeId = storeId.substring(0, 11);
        StoreModel store = new StoreModel(
                shopName.getText().toString(),
                Address.getText().toString(),
                Double.parseDouble(latitude.getText().toString().trim()),
                Double.parseDouble(longitude.getText().toString().trim()),
                items,
                offers
        );
        databaseStores.child(storeId).setValue(store);
        /*String finalStoreId = storeId;
        for(String item : itemsList) {
            databaseItems.child(item).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String storeIds = snapshot.getValue().toString();
                        ArrayList<String> storeIdList =  gson.fromJson(storeIds,
                                new TypeToken<ArrayList<String>>() { }
                                        .getType());
                        storeIdList.add(finalStoreId);
                        databaseItems.child(item).setValue(gson.toJson(storeIdList));
                    }
                    else
                    {
                        ArrayList<String> storeIdList = new ArrayList<>();
                        storeIdList.add(finalStoreId);
                        databaseItems.child(item).setValue(gson.toJson(storeIdList));
                    }
                   // Log.i("Test", snapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }*/
        Log.i("Test","Items stored bruh");
        navController.navigateUp();
    }
}
