package com.guilherme.getherfy.activity.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.guilherme.getherfy.DatePickerFragment;
import com.guilherme.getherfy.TimePickerFragment;
import com.guilherme.getherfy.activity.SalaDetailActivity;
import com.guilherme.presentation.R;

public class SalaDetailReservaFragment extends Fragment {
    DialogFragment datePicker = new DatePickerFragment();
    DialogFragment timePicker = new TimePickerFragment();
    SharedPreferences configHora;


    // TODO: Rename and change types and number of parameters
    public static SalaDetailReservaFragment newInstance() {
        SalaDetailReservaFragment fragment = new SalaDetailReservaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sala_detail_reserva, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final SalaDetailActivity salaDetail = new SalaDetailActivity();

        configHora = getActivity().getSharedPreferences("IS_HORA_INICIO", 0);



        Button btnShowInfo = getView().findViewById(R.id.fragment_sala_detail_reserva_btn_info);



        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalaDetailInfoFragment infoFragment = new SalaDetailInfoFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.sala_detail_fragment, infoFragment);
                fragmentTransaction.commit();

                ((SalaDetailActivity)getActivity()).mostraBtnNovaReserva();

            }
        });

        ImageButton btnSelecionarDia = getView().findViewById(R.id.btn_selecionar_dia);
        btnSelecionarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getFragmentManager(), "Date Picker");


            }
        });

        ImageButton btnSelecionaHoraInicio = getView().findViewById(R.id.btn_hora_inicio);
        btnSelecionaHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show(getFragmentManager(), "Time Picker");
                boolean isHoraInicio;

                SharedPreferences.Editor editor = configHora.edit();
                isHoraInicio = true;

                editor.putBoolean("isHoraInicio", isHoraInicio).commit();

            }
        });
        ImageButton btnSelecionaHoraFim = getView().findViewById(R.id.btn_hora_final);
        btnSelecionaHoraFim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timePicker.show(getFragmentManager(), "Time Picker");
                boolean isHoraInicio;

                SharedPreferences.Editor editor = configHora.edit();
                isHoraInicio = false;

                editor.putBoolean("isHoraInicio", isHoraInicio).commit();

            }

        });



    }

}
