package com.everton.pilltime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everton.pilltime.R;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.models.Idoso;

import java.text.SimpleDateFormat;
import java.util.List;

public class IdosoAdapter extends RecyclerView.Adapter<IdosoAdapter.ViewHolder> {

    private List<IdosoDTO> idosoList;

    public IdosoAdapter(List<IdosoDTO> idosoList) {
        this.idosoList = idosoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_idoso, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IdosoDTO idosoDTO = idosoList.get(position);
        holder.nomeTextView.setText(idosoDTO.getNome());
        holder.cpfTextView.setText(idosoDTO.getCpf());

        // Formate a data de nascimento
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(idosoDTO.getDataNascimento());
        holder.dataNascimentoTextView.setText(formattedDate);
        holder.telefoneTextView.setText(idosoDTO.getTelefone());
    }
    @Override
    public int getItemCount() {
        return idosoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView;
        TextView cpfTextView;
        TextView dataNascimentoTextView;
        TextView telefoneTextView;

        TextView idadeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeIdosoTextView);
            cpfTextView = itemView.findViewById(R.id.cpfIdosoTextView);
            telefoneTextView = itemView.findViewById(R.id.telefoneIdosoTextView);
            dataNascimentoTextView = itemView.findViewById(R.id.dataNascimentoIdosoTextView);
        }
    }
}
