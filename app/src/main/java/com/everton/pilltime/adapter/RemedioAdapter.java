package com.everton.pilltime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everton.pilltime.R;
import com.everton.pilltime.models.Remedio;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RemedioAdapter extends RecyclerView.Adapter<RemedioAdapter.ViewHolder> {

    private List<Remedio> remedioList;

    public RemedioAdapter(List<Remedio> remedioList) {
        this.remedioList = remedioList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                                                                    //ta inflando o layout item_remedio criado no arquivo layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remedio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Remedio remedio = remedioList.get(position);
        holder.nomeTextView.setText(remedio.getNome());
        holder.marcaTextView.setText(remedio.getMarcaRemedio());

        if (remedio.getDataValidade() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dataValidadeStr = simpleDateFormat.format(remedio.getDataValidade());
            holder.dataValidadeTextView.setText(dataValidadeStr);
        } else {
            holder.dataValidadeTextView.setText("Data não disponível"); // ou ""
        }
    }



    @Override
    public int getItemCount() {
        return remedioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
        TextView marcaTextView;
        TextView dataValidadeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            marcaTextView = itemView.findViewById(R.id.marcaTextView);
            dataValidadeTextView = itemView.findViewById(R.id.dataValidadeTextView);
        }
    }
}
