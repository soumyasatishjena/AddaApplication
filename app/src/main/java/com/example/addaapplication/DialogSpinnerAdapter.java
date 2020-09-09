package com.example.addaapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.addaapplication.databinding.DialogSpinnerAdapterLayoutBinding;

import java.util.ArrayList;

import static android.view.View.GONE;

public class DialogSpinnerAdapter extends RecyclerView.Adapter<DialogSpinnerAdapter.ViewHolder> {

    private ArrayList<DialogDataDetails> arrayList;
    private LayoutInflater layoutInflater;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Context context;

    public DialogSpinnerAdapter(Context context, ArrayList<DialogDataDetails> arrayList,
                                AdapterView.OnItemClickListener onItemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DialogSpinnerAdapterLayoutBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.dialog_spinner_adapter_layout, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == arrayList.size()-1){
            holder.binding.listName.setText(arrayList.get(position).getName());
            holder.binding.line.setVisibility(GONE);
        }
        else {
            holder.binding.listName.setText(arrayList.get(position).getName());
            holder.binding.line.setVisibility(View.VISIBLE);
        }

        if(arrayList.get(position).getNameSelected() == 1)
            holder.binding.listName.setTextColor(context.getResources().getColor(R.color.colorAccent));
        else
            holder.binding.listName.setTextColor(Color.parseColor("#262626"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DialogSpinnerAdapterLayoutBinding binding;
        public ViewHolder(@NonNull DialogSpinnerAdapterLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.layoutDialog.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            for(int i=0; i<arrayList.size();i++){
                if (i==index) {
                    if (arrayList.get(i).getNameSelected()==0)
                        arrayList.get(i).setNameSelected(1);
                    index =i;
                }
                else
                    arrayList.get(i).setNameSelected(0);
            }
            onItemClickListener.onItemClick(null, view, index, view.getId());
            notifyDataSetChanged();
        }
    }
}
