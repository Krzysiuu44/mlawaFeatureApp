package com.example.myapplication;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;


public class AdapterChoice extends RecyclerView.Adapter<AdapterChoice.ViewHolder> {
    private Context context;
    private List<MutablePair<String, String>> mainList;
    public String original;
    private boolean editable;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textKey;
        public EditText textValue;
        //public ListView listView;
        public RelativeLayout relativeLayout;
        public String original;
        public MutablePair<String,String> para;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textKey = itemView.findViewById(R.id.textKey);
            textValue = itemView.findViewById(R.id.textValue);//optional
            //listView = itemView.findViewById(R.id.textValue);
            relativeLayout = itemView.findViewById(R.id.showUpProperties);
        }

        public void setPara(MutablePair<String,String> para){
            this.para = para;
            textKey.setText(para.left);// bo to klucz
            textValue.setText(para.right);// bo to wartosc do przypisania
            original = para.right; // tak just in case

            textValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // tu nic bo przed zmiana nas nie obchodzi
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    para.setRight(s.toString()); // w razie gdy sie zmieni
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // po tak samo juz nie
                }
            });
        }
    }

    public void setMainList(List<MutablePair<String, String>> list) {
        this.mainList = list;
        notifyDataSetChanged();
    }
    public void setEditable(boolean editable){
        this.editable = editable;
        notifyDataSetChanged();
    }

    public AdapterChoice(Context context, List<MutablePair<String, String>> listOfPairs) {
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
        MutablePair<String, String> para = mainList.get(position);
        holder.setPara(para);
        holder.textValue.setEnabled(editable);
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }
}
