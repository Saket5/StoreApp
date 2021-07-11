package com.saket.store;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoreFragment extends Fragment  {

    private ProgressBar progressBar;

    private StoreModel Store;

    private TextView storeName,storeAddress;

    private RecyclerView recyclerView;

    private Button Gmap;

    private ArrayList<String> productList;

    private ArrayList<String> offersList;

    private RecyclerView productRecyclerView,offersRecyclerView;

    private OffersAdapter offersAdapter;

    private ProductAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Store = getArguments().getParcelable("StoreDetails");
        storeName=view.findViewById(R.id.tv_store_name);
        storeAddress=view.findViewById(R.id.tv_store_address);
        progressBar=view.findViewById(R.id.progressBar);

        productRecyclerView=view.findViewById(R.id.rv_products);
        offersRecyclerView=view.findViewById(R.id.rv_offers);

        storeName.setText(capitailizeWord(Store.getShopName()));
        storeAddress.setText(capitailizeWord(Store.getAddress()));

        progressBar.setVisibility(View.VISIBLE);

        Gson gson = new Gson();
        productList = gson.fromJson(Store.getItems(),
                new TypeToken<ArrayList<String>>() { }
                        .getType());

        offersList = gson.fromJson(Store.getOffers(),
                new TypeToken<ArrayList<String>>() { }
                        .getType());




        view.findViewById(R.id.btn_gmap).setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putParcelable("StoreDetails",Store);
            Navigation.findNavController(view).navigate(R.id.action_shopFragment_to_mapsFragment,bundle);
        });

        setUpProductRecyclerView();
        if(offersList.size()>=1 && !(offersList.get(0).equals(""))) {
            Log.i("Store Fragment", String.valueOf(offersList.size())+"Test"+offersList.toString()+"Test");
            setUpOfferRecyclerView();
        }
        else{
            //offersList.add("No Offers");
            view.findViewById(R.id.tv_store_offers).setVisibility(View.GONE);
            view.findViewById(R.id.hsv).setVisibility(View.GONE);
        }
        Log.i("StoreFrag",productList.toString());

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.store_appbar_menu,menu);
        MenuItem item = menu.findItem(R.id.share);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent shareIndent = new Intent();
                shareIndent.setAction(Intent.ACTION_SEND);
                shareIndent.putExtra(Intent.EXTRA_TEXT,"Store Name : "+Store.getShopName()+"\nAddress : "+Store.getAddress()+"\n Map Link : \n"+"https://maps.google.com/?ll="+Store.getLat()+","+Store.getLongitute());
                shareIndent.setType("text/plain");
                startActivity(shareIndent);
                return false;
            }
        });
    }
    private void setUpProductRecyclerView () {
        progressBar.setVisibility(View.GONE);
        productAdapter = new ProductAdapter(productList);
        productRecyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        productRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        productRecyclerView.setAdapter(productAdapter);
    }

    private void setUpOfferRecyclerView(){

        offersAdapter = new OffersAdapter(offersList);

        offersRecyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        offersRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,true));
        offersRecyclerView.setAdapter(offersAdapter);

    }

    static String capitailizeWord(String str) {
        StringBuffer s = new StringBuffer();

        // Declare a character of space
        // To identify that the next character is the starting
        // of a new word
        char ch = ' ';
        for (int i = 0; i < str.length(); i++) {

            // If previous character is space and current
            // character is not space then it shows that
            // current letter is the starting of the word
            if ((ch == ' ' || ch==',' )&& (str.charAt(i) != ' '&&str.charAt(i)!=','))
                s.append(Character.toUpperCase(str.charAt(i)));
            else
                s.append(str.charAt(i));
            ch = str.charAt(i);
        }

        // Return the string with trimming
        return s.toString().trim();
    }
}