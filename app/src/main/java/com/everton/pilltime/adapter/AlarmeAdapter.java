package com.everton.pilltime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.everton.pilltime.R;
import com.everton.pilltime.dto.AlarmeDTOInsert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.annotation.Nonnull;

public class AlarmeAdapter extends RecyclerView.Adapter<AlarmeAdapter.AlarmeViewHolder> {

    private List<AlarmeDTOInsert> alarmes;

    public AlarmeAdapter(List<AlarmeDTOInsert> listaAlarmes) {
        this.alarmes = listaAlarmes; // Correção aqui
    }

    @Nonnull
    @Override
    public AlarmeViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarme_idoso, parent, false);
        return new AlarmeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@Nonnull AlarmeAdapter.AlarmeViewHolder holder, int position) {
        AlarmeDTOInsert alarme = alarmes.get(position);

        if (alarme.getDescricao() != null) {
            holder.tvNomeRemedio.setText(alarme.getDescricao().toString());
        } else {
            holder.tvNomeRemedio.setText("Remédio desconhecido");
        }

        if (alarme.getAlarme() != null && !alarme.getAlarme().isEmpty()) {

            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(alarme.getAlarme(), formatter);

                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDate = dateTime.format(formatter);
                holder.tvHora.setText(formattedDate);
            } catch (DateTimeParseException e) {
                holder.tvHora.setText("Formato de data inválido");
            }
        } else {
            holder.tvHora.setText("Horário não definido");
        }
    }




    @Override
    public int getItemCount() {
        return alarmes.size();
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
