package com.saket.store;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    private HomeAdapter homeAdapter;

    private ProgressBar progressBar;

    NavController navController;


    ArrayList<StoreModel> storesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) requireActivity();
        // mainActivity.toolbar.setMenu(view,appbar_menu);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        navController = Navigation.findNavController(view);
        storesList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("stores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StoreModel store = snapshot.getValue(StoreModel.class);
                        if (!storesList.contains(store))
                            storesList.add(store);
                    }
                    Log.i("Home Fragmenr", storesList.toString());
                    progressBar.setVisibility(View.GONE);
                    setUpRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        /*view.findViewById(R.id.fab).setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addFragment);
        });*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.appbar_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchStores(query);
                //progressBar.setVisibility(View.VISIBLE);
                homeAdapter.getFilter().filter(query);
                progressBar.setVisibility(View.GONE);
                Log.i("Search", query);
                InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                manager

                        .hideSoftInputFromWindow(

                                getActivity().getCurrentFocus().getWindowToken(), 0);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeAdapter.getFilter().filter("");
                // progressBar.setVisibility(View.VISIBLE);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setUpRecyclerView() {

        homeAdapter = new HomeAdapter(storesList, this::onItemClick);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(homeAdapter);
    }

    private void onItemClick(int i) {
        Log.i("Home Fragment", "Item clicked ");
        Bundle bundle = new Bundle();
        bundle.putParcelable("StoreDetails", storesList.get(i));
        navController.navigate(R.id.action_homeFragment_to_shopFragment, bundle);

    }


}