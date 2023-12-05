package com.everton.pilltime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.everton.pilltime.R;
import com.everton.pilltime.dto.AlarmeDTOInsert;
import java.util.List;

public class AlarmeDTOAdapter extends RecyclerView.Adapter<AlarmeDTOAdapter.AlarmeViewHolder> {

    private final List<AlarmeDTOInsert> listaAlarmes;

    public AlarmeDTOAdapter(List<AlarmeDTOInsert> listaAlarmes) {
        this.listaAlarmes = listaAlarmes;
    }

    @NonNull
    @Override
    public AlarmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarme_cuidador, parent, false);
        return new AlarmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmeViewHolder holder, int position) {
        AlarmeDTOInsert alarme = listaAlarmes.get(position);
        holder.tvTitulo.setText(alarme.getTitulo());
        holder.tvDescricao.setText(alarme.getDescricao());
        // Se quiser exibir a data/hora do alarme, pode formatar e exibir aqui
    }

    @Override
    public int getItemCount() {
        return listaAlarmes.size();
    }

    public static class AlarmeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescricao;

        public AlarmeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            // Se tiver um TextView para hora, inicialize aqui
        }
    }
}
