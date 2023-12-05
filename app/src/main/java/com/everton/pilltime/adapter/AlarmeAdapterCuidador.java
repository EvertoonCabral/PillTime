package com.everton.pilltime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.everton.pilltime.R;
import com.everton.pilltime.alarme.Alarme;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Nonnull;

public class AlarmeAdapterCuidador extends RecyclerView.Adapter<AlarmeAdapterCuidador.AlarmeViewHolder> {

    private List<Alarme> listaAlarmes;

    public AlarmeAdapterCuidador(List<Alarme> listaAlarmes) {
        this.listaAlarmes = listaAlarmes;
    }

    @Nonnull
    @Override
    public AlarmeViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarme_cuidador, parent, false);
        return new AlarmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@Nonnull AlarmeViewHolder holder, int position) {
        Alarme alarme = listaAlarmes.get(position);
        holder.tvNomeIdoso.setText(alarme.getIdoso().getNome());
      //  holder.tvNomeRemedio.setText(alarme.getRemediosIdosos().get(0).getNome()); // Supondo que você pegue o primeiro remédio da lista

        // Formatação da data e hora usando LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
       // String formattedDate = alarme.getAlarme().format(formatter);
        // holder.tvHora.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return listaAlarmes.size();
    }

    public static class AlarmeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeIdoso, tvNomeRemedio, tvHora;

        public AlarmeViewHolder(@Nonnull View itemView) {
            super(itemView);
            tvNomeIdoso = itemView.findViewById(R.id.tvNomeIdoso);
            tvNomeRemedio = itemView.findViewById(R.id.tvNomeRemedio);
            tvHora = itemView.findViewById(R.id.tvHora);
        }
    }
}
