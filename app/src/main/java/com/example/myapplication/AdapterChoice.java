package com.example.myapplication;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AdapterChoice extends RecyclerView.Adapter<AdapterChoice.ViewHolder> {
    private Context context;
    private List<Pair<String, String>> mainList;
    private View textValue;
    private FragmentManager fragmentManager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textKey;
        public static EditText textValue;
        //public ListView listView;
        public RelativeLayout relativeLayout;

        public static void changeAttribute(boolean a, long b) {
            //textValue.setFocusableInTouchMode(a);
            textValue.setEnabled(a);
            Fragment rSum = new Fragment();
            MainActivity.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new ChosenOne(b))
                    .addToBackStack(null)
                    .commit();
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textKey = itemView.findViewById(R.id.textKey);
            textValue = itemView.findViewById(R.id.textValue);//optional
            //listView = itemView.findViewById(R.id.textValue);
            relativeLayout = itemView.findViewById(R.id.showUpProperties);
        }
    }

    public void setMainList(List<Pair<String, String>> list) {
        this.mainList = list;
        notifyDataSetChanged();
    }

    public AdapterChoice(Context context, List<Pair<String, String>> listOfPairs) {
        this.context = context;
        mainList = listOfPairs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.value_display, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, String> para = mainList.get(position);
        holder.textKey.setText(para.first);
        holder.textValue.setText(para.second);
        //holder.listView.setAdapter(new ArrayAdapter<String>(Context, Layout, para.second));

    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }
}
