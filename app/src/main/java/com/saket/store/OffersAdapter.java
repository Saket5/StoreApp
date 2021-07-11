package com.saket.store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    ArrayList<String> OffersList;

    public OffersAdapter(ArrayList<String> OffersList) {
        this.OffersList = OffersList;
    }

    @NonNull
    @NotNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new OffersAdapter.OffersViewHolder(
                layoutInflater.inflate(R.layout.item_offers, parent, false));
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


    @Override
    public void onBindViewHolder(@NonNull @NotNull OffersAdapter.OffersViewHolder holder, int position) {
        holder.itemName.setText(capitailizeWord(OffersList.get(position)));
    }

    @Override
    public int getItemCount() {
        return OffersList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        public OffersViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.tv_offer);
        }
    }
}
