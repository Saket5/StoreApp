package com.saket.store;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;


public class HomeAdapter
        extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements Filterable {

    private final ArrayList<StoreModel> stores;
    private final ArrayList<StoreModel> storesListALl;

    private static HomeOnClickListener onClickListener;


    HomeAdapter(ArrayList<StoreModel> stores, HomeOnClickListener onClickListener) {

        this.stores = stores;
        this.storesListALl = new ArrayList<>(stores);
        this.onClickListener = onClickListener;
    }


    @NonNull
    @NotNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
                                             int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new HomeViewHolder(
                layoutInflater.inflate(R.layout.store_item, parent, false));
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
            if ((ch == ' ' || ch == ',') && (str.charAt(i) != ' ' && str.charAt(i) != ','))
                s.append(Character.toUpperCase(str.charAt(i)));
            else
                s.append(str.charAt(i));
            ch = str.charAt(i);
        }

        // Return the string with trimming
        return s.toString().trim();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeViewHolder holder, int position) {

        StoreModel store = stores.get(position);
        holder.storeName.setText(capitailizeWord(store.getShopName()));
        holder.itemView.setOnClickListener(v ->
                onClickListener.onItemClick(position));
    }


    @Override
    public int getItemCount() {

        return stores.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<StoreModel> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(storesListALl);
            } else {
                for (StoreModel store : storesListALl) {
                    String itemsString = store.getItems();
                    Gson gson = new Gson();
                    ArrayList<String> itemsList = gson.fromJson(itemsString,
                            new TypeToken<ArrayList<String>>() {
                            }
                                    .getType());
                    for (String item : itemsList) {
                        if (item.contains(constraint.toString().toUpperCase())) {
                            filteredList.add(store);
                        }
                    }
                }

            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stores.clear();
            stores.addAll((Collection<? extends StoreModel>) results.values);
            notifyDataSetChanged();

        }
    };


    public interface HomeOnClickListener {

        void onItemClick(int position);

    }


    static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView storeName;

        public HomeViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);
            storeName = itemView.findViewById(R.id.tv_store_name);

        }

        @Override
        public void onClick(View v) {
            HomeAdapter.onClickListener.onItemClick(getAdapterPosition());
        }
    }

}

