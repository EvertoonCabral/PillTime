package com.everton.pilltime.UI.support;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.everton.pilltime.databinding.FragmentSupportBinding; // Importe a classe de binding gerada

public class SupportFragment extends Fragment {

    // Declare a variável para a classe de binding
    private FragmentSupportBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inicialize a classe de binding
        binding = FragmentSupportBinding.inflate(inflater, container, false);

        // Retorne a raiz da view
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Evite vazamentos de memória
        binding = null;
    }
}
