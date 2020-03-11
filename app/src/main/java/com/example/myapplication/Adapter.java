package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<Feature> list = Collections.emptyList();


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public TextView textView2;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            relativeLayout = itemView.findViewById(R.id.RelativeLayout);//tu musi byc ten xml fragmentowy
        }
    }


    public void setList(List<Feature> list) {
        this.list = list;
        notifyDataSetChanged(); //data changed
    }


    public Adapter(Context context, List<Feature> itemList) {
        this.context = context;
        list = itemList;
        notifyDataSetChanged(); //data changed
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = mInflater.inflate(R.layout.RecyclerView, parent, false);// tu leci template takiego layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example, parent, false); // false bo nie ma z root
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feature feature = list.get(position);
        holder.textView2.setText(feature.getType());
        holder.textView1.setText(Long.toString(feature.getId()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "CLICK: " + feature.id, Toast.LENGTH_LONG).show();
                MainActivity.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ChosenOne(feature.id))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}